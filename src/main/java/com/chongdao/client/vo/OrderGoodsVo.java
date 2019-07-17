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

    private BigDecimal totalPrice;

    private String createTime;

    private Integer userId;

    private BigDecimal goodsPrice;

    private BigDecimal discountPrice;

    private BigDecimal goodsTotalPrice;

    private String areaCode;

    private Integer shopId;
}
