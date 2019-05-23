package com.chongdao.client.dto;

import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.OrderLog;
import com.chongdao.client.enums.OrderStatusEnum;
import com.chongdao.client.enums.PayPlatformEnum;

public class OrderLogDTO {

    public static OrderLog addOrderLog(OrderInfo orderInfo){
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        orderLog.setOrderNo(orderInfo.getOrderNo());
        orderLog.setOrderType(PayPlatformEnum.ALI_PAY.getCode());
        return orderLog;

    }
}
