package com.chongdao.client.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单商品Vo
 */
@Data
public class OrderGoodsVo {

    private String orderNo;

    private Integer goodsId;

    private String goodsName;

    private String goodsIcon;

    private BigDecimal currentPrice;

    private Integer quantity;

    private Double discount = 0.0d;

    //第二件折扣
    private Double reDiscount = 0.0d;

    private String reDiscountDesc; //第二件描述

    private BigDecimal totalPrice;

    private String createTime;

    private Integer userId;

    private BigDecimal goodsPrice;

    private BigDecimal discountPrice;

    private BigDecimal reDiscountPrice;

    private BigDecimal goodsTotalPrice;

    private String areaCode;

    private Integer shopId;

    private BigDecimal totalDiscount = BigDecimal.ZERO;
}
