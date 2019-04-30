package com.chongdao.client.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细Vo
 */
@Data
public class OrderGoodsVo {

    private Long orderNo;

    private Integer goodsId;

    private String goodsName;

    private String goodsIcon;

    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String createTime;
}
