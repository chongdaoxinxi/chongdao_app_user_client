package com.chongdao.client.entitys;


import com.chongdao.client.utils.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


/** 
 * @Author onlineS
 * @Description 商品
 * @Date 17:39 2019/4/18
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Good {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String icon;

	private BigDecimal price = BigDecimal.ZERO;

	private Double discount = 0.0D;

	//第二件折扣
	private Double reDiscount = 0.0D;

	private Integer unit;

	private String unitName;

	private String des;

	private Integer goodsTypeId;

	private Integer shopId;

	//提高系数默认为1
	private Double ratio = 1.0D;

	private BigDecimal ratioPrice = price.multiply(new BigDecimal(ratio)).setScale(2);

	private Integer stock = 0;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	private Byte status = 1;

	private Integer moduleId;

	private Integer categoryId;

	private Integer sales = 0;


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


	@Transient
	private String categoryName;

	@Transient
	private Integer type;

	@Transient
	private String couponName;

	@Transient
	private Integer couponId;

	public Good(Integer id, String name, String icon, BigDecimal price, Double discount,Double reDiscount,
				Integer unit, String unitName, String des, Integer goodsTypeId, Integer shopId,
				Double ratio, BigDecimal ratioPrice, Integer stock, Date createTime, Date updateTime,
				Byte status, Integer moduleId, Integer categoryId, Integer sales, String typeName, Integer brandId,
				Integer scopeId, Integer petCategoryId, String bathingServiceId,Integer sort,String areaCode,String qrCode,String shopIds) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.price = price;
		this.discount = discount;
		this.reDiscount = reDiscount;
		this.unit = unit;
		this.unitName = unitName;
		this.des = des;
		this.goodsTypeId = goodsTypeId;
		this.shopId = shopId;
		this.ratio = ratio;
		this.ratioPrice = ratioPrice;
		this.stock = stock;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.moduleId = moduleId;
		this.categoryId = categoryId;
		this.sales = sales;
		this.typeName = typeName;
		this.brandId = brandId;
		this.scopeId = scopeId;
		this.petCategoryId = petCategoryId;
		this.bathingServiceId = bathingServiceId;
		this.sort = sort;
		this.areaCode = areaCode;
		this.qrCode = qrCode;
		this.shopIds = shopIds;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Good good = (Good) o;
		return id.equals(good.id) &&
				Objects.equals(name, good.name) &&
				Objects.equals(icon, good.icon) &&
				Objects.equals(price, good.price) &&
				Objects.equals(discount, good.discount) &&
				Objects.equals(reDiscount, good.reDiscount) &&
				Objects.equals(unit, good.unit) &&
				Objects.equals(unitName, good.unitName) &&
				Objects.equals(des, good.des) &&
				Objects.equals(goodsTypeId, good.goodsTypeId) &&
				shopId.equals(good.shopId) &&
				Objects.equals(ratio, good.ratio) &&
				Objects.equals(ratioPrice, good.ratioPrice) &&
				Objects.equals(stock, good.stock) &&
				Objects.equals(createTime, good.createTime) &&
				Objects.equals(updateTime, good.updateTime) &&
				Objects.equals(status, good.status) &&
				Objects.equals(moduleId, good.moduleId) &&
				Objects.equals(categoryId, good.categoryId) &&
				Objects.equals(sales, good.sales) &&
				Objects.equals(typeName, good.typeName) &&
				Objects.equals(brandId, good.brandId) &&
				Objects.equals(scopeId, good.scopeId) &&
				Objects.equals(petCategoryId, good.petCategoryId) &&
				Objects.equals(bathingServiceId, good.bathingServiceId) &&
				Objects.equals(sort, good.sort) &&
				Objects.equals(areaCode, good.areaCode) &&
				Objects.equals(categoryName, good.categoryName) &&
				Objects.equals(type, good.type) &&
				Objects.equals(couponName, good.couponName) &&
				Objects.equals(couponId, good.couponId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, icon, price, discount, reDiscount, unit, unitName, des, goodsTypeId, shopId, ratio, ratioPrice, stock, createTime, updateTime, status, moduleId, categoryId, sales, typeName, brandId, scopeId, petCategoryId, bathingServiceId, sort, areaCode, categoryName, type, couponName, couponId);
	}
}