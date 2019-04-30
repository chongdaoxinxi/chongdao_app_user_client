package com.chongdao.client.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartGoodsVo {

    private Integer id;
    private Integer userId;
    private Integer goodsId;
    private Integer quantity;
    private String goodsName;
    private BigDecimal goodsPrice;
    private Integer goodsStatus;
    private BigDecimal goodsTotalPrice;
    private Integer productChecked;
    private Integer goodsChecked;





}
