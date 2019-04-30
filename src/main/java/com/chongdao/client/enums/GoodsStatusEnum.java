package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum GoodsStatusEnum {


    GOODS(1,"商品"),
    SERVICE(2,"服务"),

    ON_SALE(1,"在架"),
    ADDRESS_EMPTY(6001,"添加完地址后才可以下单哦~"),


    ;

    private int status;

    private String message;

    GoodsStatusEnum(int status, String message){
        this.status = status;
        this.message = message;
    }

}
