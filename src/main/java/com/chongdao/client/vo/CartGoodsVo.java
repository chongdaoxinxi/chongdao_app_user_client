package com.chongdao.client.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
    private Integer goodsStock;

    private Integer sellCount;
    private String des;
    private Integer shopId;
    private String shopName;
    private Integer isService;
    private Integer cardId;

    private Double discount;

    private Integer goodsChecked;


}
