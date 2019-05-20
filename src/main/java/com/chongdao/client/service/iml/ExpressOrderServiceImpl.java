package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.enums.ManageStatusEnum;
import com.chongdao.client.enums.OrderStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.OrderInfoRepository;
import com.chongdao.client.service.ExpressOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/20
 * @Version 1.0
 **/
@Service
public class ExpressOrderServiceImpl implements ExpressOrderService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;

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
                    orderInfoRepository.saveAndFlush(odi);

                    //短信通知用户
                    //TODO
                    return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
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
                    orderInfoRepository.saveAndFlush(odi);
                    //短信通知  用户/商家
                    //TODO
                    return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
                }
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
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
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }

    /**
     * 取消订单(状态变为-1)-----------疑问 此方法存在合理嘛
     *
     * @param expressId
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse cancelOrder(Integer expressId, Integer orderId) {
        return null;
    }
}
