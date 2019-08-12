package com.chongdao.client.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderGoodsVo that = (OrderGoodsVo) o;
        return Objects.equals(orderNo, that.orderNo) &&
                goodsId.equals(that.goodsId) &&
                Objects.equals(goodsName, that.goodsName) &&
                Objects.equals(goodsIcon, that.goodsIcon) &&
                Objects.equals(currentPrice, that.currentPrice) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(reDiscount, that.reDiscount) &&
                Objects.equals(reDiscountDesc, that.reDiscountDesc) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(goodsPrice, that.goodsPrice) &&
                Objects.equals(discountPrice, that.discountPrice) &&
                Objects.equals(reDiscountPrice, that.reDiscountPrice) &&
                Objects.equals(goodsTotalPrice, that.goodsTotalPrice) &&
                Objects.equals(areaCode, that.areaCode) &&
                shopId.equals(that.shopId) &&
                Objects.equals(totalDiscount, that.totalDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, goodsId, goodsName, goodsIcon, currentPrice, quantity, discount, reDiscount, reDiscountDesc, totalPrice, createTime, userId, goodsPrice, discountPrice, reDiscountPrice, goodsTotalPrice, areaCode, shopId, totalDiscount);
    }
}
