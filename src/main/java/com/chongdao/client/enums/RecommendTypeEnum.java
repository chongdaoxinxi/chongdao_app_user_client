package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum RecommendTypeEnum {
    USER_ROLE(1, "用户"),
    EXPRESS_ROLE(2, "配送员"),
    SHOP_ROLE(3, "商家"),
    ORDER(1, "订单"),
    MEDICAL_INSURANCE(2, "医疗保险"),
    FAMILY_INSURANCE(3, "家责保险"),
    ORDER_REWARD_PERCENT(5, "首单返现比例"),
    ORDER_REWARD_MAX(58, "首单返现上限"),
    INSURANCE_REWARD_PERCENT(5, "保险返现比例")
    ;

    private Integer code;
    private String name;

    RecommendTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
