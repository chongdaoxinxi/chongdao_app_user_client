package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum PaymentTypeEnum {
    ALI_PAY(1,"支付宝支付"),
    WX_PAY(2,"微信支付")
    ;

    private int status ;
    private String value ;

    PaymentTypeEnum(int status, String value) {
        this.status = status;
        this.value = value;
    }

    public static PaymentTypeEnum codeOf(int code){
        for (PaymentTypeEnum paymentTypeEnum:values()
             ) {
            if (paymentTypeEnum.getStatus() == code){
                return paymentTypeEnum;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");

    }
}
