package com.chongdao.client.client;

import com.chongdao.client.client.dto.*;
import com.chongdao.client.entitys.Express;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fenglong
 * @date 2019-09-24 17:36
 */
@Component
@Slf4j
public class OrderFeignClientFallBack implements OrderFeignClient{


    @Override
    public List<OrderInfo> list(Integer shopId, String orderStatus) {
        log.error("【 订单列表服务异常 shopId:{}, orderStatus: {} 】",shopId,orderStatus);
        return null;
    }

    @Override
    public List<OrderDetail> detail(Integer orderId) {
        log.error("【 订单详情服务异常 orderId: {} 】",orderId);
        return null;
    }

    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        log.error("【 获取订单异常 orderNo: {} 】",orderNo);
        return null;
    }

    @Override
    public Express getExpressById(Integer expressId) {
        log.error("【 获取配送员异常 expressId: {} 】",expressId);
        return null;
    }

    @Override
    public Shop getShop(Integer shopId) {
        log.error("【 获取店铺异常 shopId: {} 】",shopId);
        return null;
    }

    @Override
    public User getUser(Integer userId) {
        log.error("【 获取用户异常 userId: {} 】",userId);
        return null;
    }

    /**
     * 获取订单地址
     * @param orderId
     * @return
     */
    @Override
    public List<OrderAddress> getAddress(Integer orderId) {
        log.error("【 获取订单地址异常  orderId: {} 】",orderId);
        return null;
    }

    @Override
    public void updateOrderStatus(String orderNo, Integer status) {
        log.error("【 更新订单状态异常  orderNo: {}, status: {} 】",orderNo,status);
    }
}
