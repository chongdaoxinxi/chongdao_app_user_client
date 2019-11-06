package com.chongdao.client.vo;


import com.chongdao.client.entitys.BathingService;
import com.chongdao.client.entitys.coupon.CouponInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GoodsDetailVo {

    private Integer goodsId;

    /** 商品名称*/
    private String name;

    /** 原价*/
    private BigDecimal price;

    /** 折扣价*/
    private BigDecimal discountPrice;

    //第二件折扣
    private Double reDiscount;

    private Double discount;
    private String reDiscountDesc; //第二件描述

    /** 销量*/
    private Integer sales;

    /** 满减*/
    private List<CouponInfo> couponInfoFullList;

    //优惠券
    private List<CouponInfo> couponInfoList;

    /** 地址 */
    private String address;

    /** 店铺id */
    private Integer shopId;

    /** 店铺名称 */
    private String shopName;

    /** 营业时间 */
    private String  startBusinessHours;

    /** 结束营业时间 */
    private String  endBusinessHours;

    /** 商品描述 */
    private String des;

    private Double shopGrade;


    /** 品牌id */
    private Integer brandId;

    private String brandName; //品牌
    private Integer unit;      //重量id
    private String unitName;   //重量显示
    private String scopeName; //适用阶段
    private List<BathingService> serviceContent;   //洗澡服务内容


    private Integer orderId;

    //收藏状态  1 收藏 0取消收藏
    private Integer concernStatus;

    private Integer valid;

    private String img;

    private String shopLogo;

}
