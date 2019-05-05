package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum PayPlatformEnum {

    ALI_PAY(1,"支付宝"),
    WX_PAY(2,"微信")
    ;

    private int code ;
    private String value ;

    PayPlatformEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
