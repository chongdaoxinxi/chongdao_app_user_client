package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    ORDER_CLOSE(-3,"订单关闭"),
    CANCELED(-2,"已取消"),
    NO_PAY(-1,"未支付"),
    PAID(0,"已付款"),
    ORDER_SUCCESS(3,"订单完成"),


    ORDER_PRE(1,"预下单"),
    ORDER_CREATE(2,"提交订单"),



    ;

    private int status;

    private String message;

    OrderStatusEnum(int status, String message){
        this.status = status;
        this.message = message;
    }
}
