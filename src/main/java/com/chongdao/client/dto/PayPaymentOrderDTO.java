package com.chongdao.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PayPaymentOrderDTO {

    //商品名称
    private String paySubject;

    //订单号
    private String payOutTradeNo;

    //支付金额
    private BigDecimal payTotalAmount;

    private Integer userId;


}
