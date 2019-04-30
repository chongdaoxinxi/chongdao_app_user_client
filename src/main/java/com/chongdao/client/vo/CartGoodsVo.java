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
    private Integer goodsStock;
    private Integer productChecked;



    private Integer sellCount;
    private String des;
    private Integer shopId;
    private String shopName;

    private BigDecimal totalPrice;

    //0.商品 1.服务
    private Integer isService;

    //0.双程 1.单程
    private Integer orderType;

    private BigDecimal discountPrice;

    private Integer cardId;

    private Double discount;

    private BigDecimal servicePrice;



    private CouponVO couponVO;

    private Integer serviceCouponCount;

    private Integer goodsCouponCount;

    private Integer goodsChecked;





}
