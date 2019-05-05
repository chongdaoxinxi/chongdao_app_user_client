package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    ORDER_CLOSE(-3,"订单关闭"),
    CANCELED(-2,"已取消"),
    NO_PAY(-1,"未支付"),
    PAID(1,"已付款"),
    ACCEPTED_ORDER(2,"已接单"),
    IN_SERVICE_ORDER(3,"服务中"),

    ORDER_SUCCESS(4,"订单完成"),


    ORDER_PRE(1,"预下单"),
    ORDER_CREATE(2,"提交订单"),


    ADDRESS_NOT_EMPTY(501,"请选择地址后下单"),



    ;

    private int status;

    private String message;

    OrderStatusEnum(int status, String message){
        this.status = status;
        this.message = message;
    }
}
