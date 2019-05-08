package com.chongdao.client.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
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
}
