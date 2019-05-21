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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                    //此处是否应该存入配送员接单时间
                    //TODO
                    OrderInfo orderInfo = orderInfoRepository.saveAndFlush(odi);

                    //短信通知用户
                    //TODO
                    expressAcceptOrderSmsSender(orderInfo, "");
                    return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 配送员接单短信通知(->用户)
     * @param orderInfo
     */
    private void  expressAcceptOrderSmsSender(OrderInfo orderInfo, String pointedMsg) {
        Integer shopId = orderInfo.getShopId();
        if(shopId != null) {
            Shop shop = shopRepository.findById(shopId).orElse(null);
            if(shop != null) {
                String shopName = shop.getShopName();
                List<String> phoneList = smsService.getUserPhoneListByOrderId(orderInfo.getId());
                //TODO 具体发什么待定
                String msg = "";
                if(pointedMsg != null && !pointedMsg.equals("")) {
                    msg = pointedMsg;
                }
                smsService.customOrderMsgSenderPatch(msg, shopName, orderInfo.getOrderNo(), phoneList);
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
        if(expressId != null && orderId != null) {
            OrderInfo odi = orderInfoRepository.findById(orderId).orElse(null);
            if(odi != null) {
                Integer serviceType = odi.getServiceType();
                if(serviceType == 1) {
                    //单程 直接转掉服务完成方法
                    return serviceComplete(expressId, orderId);
                } else if(serviceType == 2) {
                    //双程
                    odi.setOrderStatus(OrderStatusEnum.EXPRESS_DELIVERY_COMPLETE.getStatus());
                    OrderInfo orderInfo = orderInfoRepository.saveAndFlush(odi);
                    //短信通知  用户/商家
                    //TODO
                    expressArriveShopSmsSender(orderInfo);
                    return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 配送员到店短信通知(->店铺+用户)
     * @param orderInfo
     */
    private void expressArriveShopSmsSender(OrderInfo orderInfo) {
        Integer shopId = orderInfo.getShopId();
        if(shopId != null) {
            Shop shop = shopRepository.findById(shopId).orElse(null);
            if(shop != null) {
                String shopName = shop.getShopName();
                List<String> userPhoneList = smsService.getUserPhoneListByOrderId(orderInfo.getId());
                //推送用户
                //TODO 具体发什么待定
                smsService.customOrderMsgSenderPatch("", shopName, orderInfo.getOrderNo(), userPhoneList);
                String shopPhone = smsService.getShopPhoneByOrderId(orderInfo.getId());
                //推送商家
                //TODO 具体发什么待定
                smsService.customOrderMsgSenderSimple("", shopName, orderInfo.getOrderNo(), shopPhone);
            }
        }
    }

    /**
     * 服务完成
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse serviceComplete(Integer expressId, Integer orderId) {
        if(expressId != null && orderId != null) {
            OrderInfo odi = orderInfoRepository.findById(orderId).orElse(null);
            if(odi != null) {
                //TODO ???单程是否应该是店铺服务完成才算订单结束呢

                OrderInfo orderInfo = orderInfoRepository.saveAndFlush(odi);
                //TODO 短信通知
                expressServiceCompleteSmsSender(orderInfo);
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 配送员服务完成通知(->用户)
     * @param orderInfo
     */
    private void expressServiceCompleteSmsSender(OrderInfo orderInfo) {
        //直接调用接单短信通知的方法, 除了发送的消息不一样其他地方都一样来着
        expressAcceptOrderSmsSender(orderInfo, "");
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
        return null;
    }

    /**
     * 配送管理员取消订单短信通知(->店铺+配送员+用户)
     * @param orderInfo
     */
    private void expressAdminCancelOrderSmsSender(OrderInfo orderInfo) {

    }
}
