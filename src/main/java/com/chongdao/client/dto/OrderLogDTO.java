package com.chongdao.client.dto;

import com.chongdao.client.entitys.*;
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

    public static OrderLog addOrderLogInsurance(InsuranceFeeRecord insuranceFeeRecord){
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(insuranceFeeRecord.getId());
        orderLog.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        orderLog.setOrderNo(insuranceFeeRecord.getOrderNo());
        orderLog.setOrderType(PayPlatformEnum.ALI_PAY.getCode());
        return orderLog;
    }
}
