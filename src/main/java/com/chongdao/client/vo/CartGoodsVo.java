package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartGoodsVo {

    private Integer id;
    private Integer userId;
    private Integer goodsId;
    private Integer shopId;
    private Integer quantity;
    private String goodsName;
    private Integer categoryId;
    private BigDecimal goodsPrice;
    private Integer goodsStatus;
    private BigDecimal goodsTotalPrice;
    private Integer productChecked;
    private Byte goodsChecked;

    private BigDecimal discountPrice;
    private Double discount = 0.0d;
    private Double reDiscount = 0.0d;





}
