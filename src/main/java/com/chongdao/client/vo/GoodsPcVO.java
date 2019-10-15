package com.chongdao.client.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/15
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GoodsPcVO {
    private Integer id;
    private String name;
    private String icon;
    private BigDecimal price;
    private Double discount;
    //第二件折扣
    private Double reDiscount;
    private String unit;
    private String unitName;
    private String des;
    private Integer goodsTypeId;
    private Integer shopId;
    //提高系数默认为1
    private Double ratio;
    private BigDecimal ratioPrice;
    private Integer stock;
    private Date createTime;
    private Date updateTime;
    private Byte status;
    private Integer moduleId;
    private Integer categoryId;
    private Integer sales;
    //猫、狗的类型
    private String typeName;
    private Integer brandId;
    //猫狗粮适用范围id
    private Integer scopeId;
    //宠物类型id
    private Integer petCategoryId;
    //洗澡服务内容id （多选）
    private String bathingServiceId;
    private Integer sort;
    private String areaCode;
    private String qrCode;
    //该字段是官方店铺上传的商品，供普通商家勾选使用
    private String shopIds;
    private String categoryName;
    private String goodsTypeName;
}
