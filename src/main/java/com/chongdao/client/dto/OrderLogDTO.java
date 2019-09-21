package com.chongdao.client.dto;

import com.chongdao.client.entitys.HtOrderInfo;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.OrderInfoRe;
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

    public static OrderLog addOrderLogRe(OrderInfoRe orderInfoRe){
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfoRe.getId());
        orderLog.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        orderLog.setOrderNo(orderInfoRe.getReOrderNo());
        orderLog.setOrderType(PayPlatformEnum.ALI_PAY.getCode());
        return orderLog;
    }

    public static OrderLog addOrderLogHT(HtOrderInfo htOrderInfo){
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(htOrderInfo.getId());
        orderLog.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        orderLog.setOrderNo(htOrderInfo.getHtOrderNo());
        orderLog.setOrderType(PayPlatformEnum.ALI_PAY.getCode());
        return orderLog;
    }
}
