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
    private BigDecimal goodsPrice;
    private Integer goodsStatus;
    private BigDecimal goodsTotalPrice;
    private Integer productChecked;
    private Byte goodsChecked;





}
