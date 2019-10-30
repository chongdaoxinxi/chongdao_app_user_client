package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Express;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.ManageStatusEnum;
import com.chongdao.client.enums.OrderStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.ExpressMapper;
import com.chongdao.client.repository.ExpressRepository;
import com.chongdao.client.repository.OrderInfoRepository;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.service.ExpressOrderService;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.service.insurance.InsuranceService;
import com.chongdao.client.utils.sms.SMSUtil;
import com.chongdao.client.vo.CompleteOrderStaticsSingleVO;
import com.chongdao.client.vo.CompleteOrderStaticsVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description 配送端
 * @Author onlineS
 * @Date 2019/5/20
 * @Version 1.0
 **/
@Service
public class ExpressOrderServiceImpl implements ExpressOrderService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private SmsService smsService;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private SMSUtil smsUtil;
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private ExpressMapper expressMapper;
    @Autowired
    private ExpressRepository expressRepository;

    /**
     * 接单
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse expressAcceptOrder(Integer expressId, Integer orderId) {
        if (expressId != null && orderId != null) {
            OrderInfo odi = orderInfoRepository.findById(orderId).orElse(null);
            if (odi != null) {
                Integer orderStatus = odi.getOrderStatus();
                if (orderStatus != OrderStatusEnum.ACCEPTED_ORDER.getStatus()) {
                    return ResultResponse.createByErrorCodeMessage(ManageStatusEnum.ORDER_CANNT_ACCEPT.getStatus(), ManageStatusEnum.ORDER_CANNT_ACCEPT.getMessage());
                } else {
                    //保存配送员id及更新状态至7
                    odi.setExpressId(orderId);
                    odi.setOrderStatus(OrderStatusEnum.EXPRESS_ACCEPTED_ORDER.getStatus());
                    odi.setExpressReceiveTime(new Date());//配送员接单时间
                    OrderInfo orderInfo = orderInfoRepository.saveAndFlush(odi);

                    //短信通知用户
                    expressAcceptOrderSmsSender(orderInfo, "");
                    return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 配送员接单短信通知(->用户)
     *
     * @param orderInfo
     */
    private void expressAcceptOrderSmsSender(OrderInfo orderInfo, String pointedMsg) {
        Integer shopId = orderInfo.getShopId();
        if (shopId != null) {
            Shop shop = shopRepository.findById(shopId).orElse(null);
            if (shop != null) {
                List<String> phoneList = smsService.getUserPhoneListByOrderId(orderInfo.getId());
                //推送用户
                if (phoneList.size() > 0) {
                    String msg = smsUtil.getOrderAssignedUser();
                    if (pointedMsg != null && !pointedMsg.equals("")) {
                        msg = pointedMsg;
                    }
                    smsService.customOrderMsgSenderPatchNoShopName(msg, orderInfo.getOrderNo(), phoneList);
                }
                //推送配送员自己
                String phone = smsService.getExpressPhoneByOrderId(orderInfo.getId());
                if (StringUtils.isNotBlank(phone)) {
                    smsService.customOrderMsgSenderSimpleNoShopName(smsUtil.getOrderAssignedExpress(), orderInfo.getOrderNo(), phone);
                }
            }
        }
    }

    /**
     * 接到
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse received(Integer expressId, Integer orderId) throws IOException {
        //仅推送短信给用户, 使用户体验更好
        List<String> phoneList = smsService.getUserPhoneListByOrderId(orderId);
        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);
        Integer isService = orderInfo.getIsService();
        Integer serviceType = orderInfo.getServiceType();
        if (isService == -1) {
            //商品
            orderInfo.setOrderStatus(OrderStatusEnum.EXPRESS_START_SERVICE.getStatus());//开始配送
            smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getSingleTripGoodServiceStartUser(), orderInfo.getOrderNo(), phoneList);
        } else if (isService == 1) {
            //服务
            // 接到宠物开始配送订单相关逻辑
            if (serviceType == 1) {
                //单程
                orderInfo.setOrderStatus(OrderStatusEnum.EXPRESS_START_SERVICE.getStatus());//开始配送
                smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getSingleTripPetServiceStartUser(), orderInfo.getOrderNo(), phoneList);
            } else if (serviceType == 2) {
                //双程
                Integer orderStatus = orderInfo.getOrderStatus();
                if (orderStatus == OrderStatusEnum.EXPRESS_ACCEPTED_ORDER.getStatus()) {
                    orderInfo.setOrderStatus(OrderStatusEnum.EXPRESS_START_SERVICE.getStatus());//开始配送
                    smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getSingleTripPetServiceStartUser(), orderInfo.getOrderNo(), phoneList);
                } else if (orderStatus == OrderStatusEnum.SHOP_COMPLETE_SERVICE.getStatus()) {
                    orderInfo.setOrderStatus(OrderStatusEnum.EXPRESS_START_DELIVERY_SERVICE.getStatus());//返程开始配送
                    smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getSingleTripPetServiceStartUser(), orderInfo.getOrderNo(), phoneList);
                }
            }
        }
        orderInfoRepository.save(orderInfo);
        //判断是否投保运输险
        if (orderInfo.getPetCount() != null && orderInfo.getPetCount() > 0 && serviceType != null && serviceType != 3) {
            //非到店自取, 且宠物数量大于0的
            insuranceService.insuranceZcg(orderInfo);
        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 送达
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse delivery(Integer expressId, Integer orderId) {
        //仅推送短信给用户, 使用户体验更好
        List<String> phoneList = smsService.getUserPhoneListByOrderId(orderId);
        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);
        Integer isService = orderInfo.getIsService();
        Integer serviceType = orderInfo.getServiceType();
        if (isService == -1) {
            //商品
            orderInfo.setOrderStatus(OrderStatusEnum.EXPRESS_DELIVERY_COMPLETE.getStatus());//商品送达
            orderInfo.setExpressFinishTime(new Date());//配送完成时间
            smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderGoodsServedUser(), orderInfo.getOrderNo(), phoneList);
            orderComplete(orderInfo);
        } else if (isService == 1) {
            //服务
            // 接到宠物开始配送订单相关逻辑
            if (serviceType == 1) {
                //单程
                orderInfo.setOrderStatus(OrderStatusEnum.EXPRESS_DELIVERY_COMPLETE.getStatus());//单程送达
                orderInfo.setExpressFinishTime(new Date());//配送员配送完成时间
                smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderPetsServedUser(), orderInfo.getOrderNo(), phoneList);
                orderComplete(orderInfo);
            } else if (serviceType == 2) {
                //双程
                Integer orderStatus = orderInfo.getOrderStatus();
                if (orderStatus == OrderStatusEnum.EXPRESS_START_SERVICE.getStatus()) {
                    orderInfo.setOrderStatus(OrderStatusEnum.EXPRESS_DELIVERY_COMPLETE.getStatus());//家至店送达
                    orderInfoRepository.save(orderInfo);
                    smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderPetsServedUser(), orderInfo.getOrderNo(), phoneList);
                } else if (orderStatus == OrderStatusEnum.EXPRESS_START_DELIVERY_SERVICE.getStatus()) {
                    orderInfo.setOrderStatus(OrderStatusEnum.EXPRESS_BACK_DELIVERY.getStatus());//店至家送达
                    orderInfo.setExpressFinishTime(new Date());//配送完成时间
                    smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderPetsServedUser(), orderInfo.getOrderNo(), phoneList);
                    orderComplete(orderInfo);
                }
            }
        }
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse getCompleteOrderStatics(Integer expressId) {
        //判断是否管理员
        if(isExpressAdmin(expressId)) {
            expressId = null;
        }
        return ResultResponse.createBySuccess(expressMapper.getCompleteOrderStatics(expressId));
    }

    private Boolean isExpressAdmin(Integer expressId) {
        boolean flag = false;
        //判断是否管理员
        List<Express> list = expressRepository.findByIdAndStatus(expressId, 1);
        if(list.size() > 0) {
            Express express = list.get(0);
            Integer expressType = express.getType();
            if(expressType != null && expressType == 2) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public ResultResponse getCompleteOrderStaticsByType(Integer expressId, Integer pageNum, Integer pageSize) {
        List<Express> list = expressRepository.findByIdAndStatus(expressId, 1);
        if(list.size() > 0) {
            Express express = list.get(0);
            Integer expressType = express.getType();
            if(expressType == 2) {
                //管理员
                expressId = null;
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        //先进行一级分页
        List<CompleteOrderStaticsVO> completeOrderStaticsList = expressMapper.getCompleteOrderStaticsGroupByWeek(expressId, "3, 4, 5, 6, 8, 9, 10, 13");
        PageInfo pageResult = new PageInfo(completeOrderStaticsList);
        pageResult.setList(completeOrderStaticsList);
        List<CompleteOrderStaticsVO> pageableList = pageResult.getList();
        //对一级分组的数组, 遍历取二级数据
        for(CompleteOrderStaticsVO cosv : pageableList) {
            Date startDate = cosv.getStartDate();
            Date endDate = cosv.getEndDate();
            List<CompleteOrderStaticsSingleVO> sonList = expressMapper.getCompleteOrderStaticsGroupByNameLimitStartAndEndDate(expressId, "3, 4, 5, 6, 8, 9, 10, 13", startDate, endDate);
            cosv.setList(sonList);
        }
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 订单状态流转为完成
     * @param orderInfo
     */
    private void orderComplete(OrderInfo orderInfo) {
        orderInfo.setOrderStatus(OrderStatusEnum.ORDER_SUCCESS.getStatus());
        orderInfoRepository.save(orderInfo);
    }

    /**
     * 取消订单(状态变为-1)-----------?? 此方法存在合理嘛
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse cancelOrder(Integer expressId, Integer orderId) {
        return Optional.ofNullable(orderId).
                flatMap(id -> orderInfoRepository.findById(id))
                .map(o -> {
                    o.setOrderStatus(-1);
                    expressAdminCancelOrderSmsSender(orderInfoRepository.saveAndFlush(o));
                    return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                })
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 配送管理员取消订单短信通知(->店铺+配送员+用户)
     *
     * @param orderInfo
     */
    private void expressAdminCancelOrderSmsSender(OrderInfo orderInfo) {
        Integer id = orderInfo.getId();
        //通知商家
        String shopPhone = smsService.getShopPhoneByOrderId(id);
        if (StringUtils.isNotBlank(shopPhone)) {
            smsService.customOrderMsgSenderSimpleNoShopName(smsUtil.getOrderExpressCancelCustom(), orderInfo.getOrderNo(), shopPhone);
        }
        //通知用户
        List<String> phoneList = smsService.getUserPhoneListByOrderId(id);
        if (phoneList.size() > 0)
            smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderExpressCancelUser(), orderInfo.getOrderNo(), phoneList);
        //通知配送员
        String expressPhone = smsService.getExpressPhoneByOrderId(id);
        if (StringUtils.isNotBlank(expressPhone)) {
            smsService.customOrderMsgSenderSimpleNoShopName(smsUtil.getOrderExpressCancelCustom(), orderInfo.getOrderNo(), expressPhone);
        }
        //通知管理员
        List<String> phoneListAdmin = smsService.getAdminPhoneListByOrderId(id);
        if (phoneListAdmin.size() > 0) {
            smsService.customOrderMsgSenderSimpleNoShopName(smsUtil.getOrderExpressCancelCustom(), orderInfo.getOrderNo(), expressPhone);
        }
    }

    //////////////////////////////////////////////////////////////////////////////以下方法暂时废弃////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 到店
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse arriveShop(Integer expressId, Integer orderId) {
        if (expressId != null && orderId != null) {
            OrderInfo odi = orderInfoRepository.findById(orderId).orElse(null);
            if (odi != null) {
                Integer serviceType = odi.getServiceType();
                //只有双程才有到店功能
                if (serviceType == 2) {
                    //双程
                    odi.setOrderStatus(OrderStatusEnum.EXPRESS_DELIVERY_COMPLETE.getStatus());
                    OrderInfo orderInfo = orderInfoRepository.saveAndFlush(odi);
                    //短信通知  用户/商家
                    expressArriveShopSmsSender(orderInfo);
                    return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 配送员到店短信通知(->店铺+用户)
     *
     * @param orderInfo
     */
    private void expressArriveShopSmsSender(OrderInfo orderInfo) {
        Integer shopId = orderInfo.getShopId();
        if (shopId != null) {
            Shop shop = shopRepository.findById(shopId).orElse(null);
            if (shop != null) {
                //推送用户
                List<String> userPhoneList = smsService.getUserPhoneListByOrderId(orderInfo.getId());
                smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderPetArrivedUser(), orderInfo.getOrderNo(), userPhoneList);
                //推送商家
                String shopPhone = smsService.getShopPhoneByOrderId(orderInfo.getId());
                smsService.customOrderMsgSenderSimpleNoShopName(smsUtil.getOrderPetArrivedShop(), orderInfo.getOrderNo(), shopPhone);
            }
        }
    }

    /**
     * 配送服务完成
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse serviceComplete(Integer expressId, Integer orderId) {
        if (expressId != null && orderId != null) {
            OrderInfo odi = orderInfoRepository.findById(orderId).orElse(null);
            if (odi != null) {
                //单程只要配送员送达就算服务完成
                odi.setOrderStatus(3);
                odi.setExpressFinishTime(new Date());
                OrderInfo orderInfo = orderInfoRepository.saveAndFlush(odi);
                //短信通知
                expressServiceCompleteSmsSender(orderInfo);
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 配送员服务完成通知(->用户)
     *
     * @param orderInfo
     */
    private void expressServiceCompleteSmsSender(OrderInfo orderInfo) {
        List<String> phoneList = smsService.getUserPhoneListByOrderId(orderInfo.getId());
        if (phoneList.size() > 0) {
            String msg = "";
            Integer serviceType = orderInfo.getServiceType();
            if (serviceType == 1) {
                //单程服务完成分两种情况: 店->家, 家->店
                Integer isService = orderInfo.getIsService();
                if (isService == -1) {
                    //商品
                    msg = smsUtil.getOrderGoodsServedUser();
                } else if (isService == 1) {
                    //服务
                    Integer singleServiceType = orderInfo.getSingleServiceType();
                    if (singleServiceType != null) {
                        if (singleServiceType == 1) {
                            //家->店
                            msg = smsUtil.getOrderPetsServedUser();
                        } else if (singleServiceType == 2) {
                            //店->家
                            msg = smsUtil.getOrderPetArrivedUser();
                        }
                    }
                }
            } else if (serviceType == 2) {
                //双程
                msg = smsUtil.getOrderPetArrivedUser();
            }
            if (StringUtils.isNotBlank(msg)) {
                smsService.customOrderMsgSenderPatchNoShopName(msg, orderInfo.getOrderNo(), phoneList);
            }
        }
    }

    /**
     * 单程(店->家), 配送员到店后开始配送时的短信通知
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse expressStartServiceInSingleTripNotice(Integer expressId, Integer orderId) {
        //仅推送短信给用户, 使用户体验更好
        List<String> phoneList = smsService.getUserPhoneListByOrderId(orderId);
        if (phoneList.size() > 0) {
            OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);
            if (orderInfo != null) {
                Integer isService = orderInfo.getIsService();
                if (isService != null) {
                    if (isService == -1) {
                        //商品
                        smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getSingleTripGoodServiceStartUser(), orderInfo.getOrderNo(), phoneList);
                        return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                    } else if (isService == 1) {
                        //服务
                        smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getSingleTripPetServiceStartUser(), orderInfo.getOrderNo(), phoneList);
                        return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                    }
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }
}
