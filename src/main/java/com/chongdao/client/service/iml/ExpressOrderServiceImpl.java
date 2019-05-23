package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.ManageStatusEnum;
import com.chongdao.client.enums.OrderStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.OrderInfoRepository;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.service.ExpressOrderService;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.utils.sms.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 接单
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
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
                    odi.setExpressReceiveTime(new Date());
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
     * 到店
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
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
        if(phoneList.size() > 0) {
            String msg = "";
            Integer serviceType = orderInfo.getServiceType();
            if (serviceType == 1) {
                //单程服务完成分两种情况: 店->家, 家->店
                Integer isService = orderInfo.getIsService();
                if(isService == -1) {
                    //商品
                    msg = smsUtil.getOrderGoodsServedUser();
                } else if(isService == 1) {
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
            if(StringUtils.isNotBlank(msg)) {
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

    /**
     * 取消订单(状态变为-1)-----------?? 此方法存在合理嘛
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
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
}