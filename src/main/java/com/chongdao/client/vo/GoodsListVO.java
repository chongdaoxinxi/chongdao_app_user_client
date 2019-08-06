package com.chongdao.client.vo;

import com.chongdao.client.entitys.BathingService;
import com.chongdao.client.entitys.coupon.CouponInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GoodsListVO {

    private Integer id;

    private String name;

    /** 商品分类 ->官方定义*/
    private Integer goodsTypeId;

    private String goodsTypeName;

    /** 商品分类 ->官方定义*/
    private Integer categoryId;

    /** 分类名称 */
    private String categoryName;

    /** 商品图片 */
    private String icon;

    private Integer shopId;

    /** 折扣 */
    private Double discount;

    //第二件折扣
    private Double reDiscount;

    private String reDiscountDesc; //第二件描述

    private BigDecimal price;

    /** 折扣价 */
    private BigDecimal discountPrice;

    /** 库存 */
    private Integer stock;

    /** 销量 */
    private Integer sales;

    /** 单位 */
    private Integer unit;
    private Integer unitName;

    /** 品牌id */
    private Integer brandId;

    private String brandName;

    private String type;


    private String typeName;


    //猫狗粮适用范围id
    private Integer scopeId;

    private String scopeName;

    //宠物类型id
    private Integer petCategoryId;

    private String petCategoryName;

    private String bathingServiceId;

    //洗澡服务内容id （多选）

    List<BathingService> bathingServiceList;

    private List<CouponInfo> couponInfoList;

    private Integer sort;










}
