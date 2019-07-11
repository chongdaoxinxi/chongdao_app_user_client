package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum GoodsStatusEnum {


    GOODS(0,"商品"),
    SERVICE(1,"服务"),

    ON_SALE(1,"在架"),
    ADDRESS_EMPTY(6001,"添加完地址后才可以下单哦~"),
    SAVE_GOODS_ERROR(6002,"商品保存失败"),
    GOODS_NOT_EXIST(6003,"商品不存在"),


    ;

    private int status;

    private String message;

    GoodsStatusEnum(int status, String message){
        this.status = status;
        this.message = message;
    }

}
