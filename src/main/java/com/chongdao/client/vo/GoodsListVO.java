package com.chongdao.client.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GoodsListVO {

    private Integer id;

    private String name;

    /** 商品分类 ->商家自定义*/
    private Integer goodTypeId;

    /** 商品分类 ->官方定义*/
    private Integer categoryId;

    /** 分类名称 */
    private String categoryName;

    /** 商品图片 */
    private String icon;

    private Integer shopId;

    /** 折扣 */
    private Double discount;

    private BigDecimal price;

    /** 折扣价 */
    private BigDecimal discountPrice;

    /** 库存 */
    private Integer stock;

    /** 销量 */
    private Integer sales;


    /** 品牌id */
    private Integer brandId;

    private String unit;

    private Byte status;

    private String typeName;

    private List<CouponVO> couponVOList;










}
