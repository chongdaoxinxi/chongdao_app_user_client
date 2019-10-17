package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum DeductPercentEnum {
    INSURANCE_FEE_DEDUCT(10, "医疗费用扣费比例"),
    CUSTOM_ORDER_DEDUCT(5, "常规订单扣费比例"),
    ;

    private Integer code;
    private String name;

    DeductPercentEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
