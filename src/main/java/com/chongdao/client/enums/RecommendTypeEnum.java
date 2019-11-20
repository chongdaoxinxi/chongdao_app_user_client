package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum RecommendTypeEnum {
    USER_ROLE(1, "用户"),
    EXPRESS_ROLE(2, "配送员"),
    SHOP_ROLE(3, "商家"),
    MEDICAL_INSURANCE(1, "医疗保险返现"),
    FAMILY_INSURANCE(2, "家责保险返现"),
    ORDER(3, "订单返现"),
    ORDER_REWARD_PERCENT(8, "首单返现比例"),
    INSURANCE_REWARD_PERCENT(8, "保险返现比例"),
    ORDER_REWARD_MAX(58, "首单返现上限")
    ;

    private Integer code;
    private String name;

    RecommendTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
