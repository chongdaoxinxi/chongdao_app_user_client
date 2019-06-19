package com.chongdao.client.entitys;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String name;

	private String icon;

	private BigDecimal price = BigDecimal.ZERO;

	private Double discount = 0.0D;

	private String unit;

	private String des;

	private Integer goodsTypeId;

	private Integer shopId;

	//提高系数默认为1
	private Double ratio = 1.0D;

	private BigDecimal ratioPrice = price.multiply(new BigDecimal(ratio)).setScale(2);

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



	@Transient
	private String categoryName;

	@Transient
	private Integer type;

	@Transient
	private String couponName;

	@Transient
	private Integer couponId;

	public Good(Integer id, String name, String icon, BigDecimal price, Double discount, String unit, String des, Integer goodsTypeId, Integer shopId, Double ratio, BigDecimal ratioPrice, Integer stock, Date createTime, Date updateTime, Byte status, Integer moduleId, Integer categoryId, Integer sales, String typeName, Integer brandId, Integer scopeId, Integer petCategoryId, String bathingServiceId) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.price = price;
		this.discount = discount;
		this.unit = unit;
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
	}


	@Override
	public String toString() {
		return "Good{" +
				"id=" + id +
				", des='" + des + '\'' +
				", discount=" + discount +
				", icon='" + icon + '\'' +
				", moduleId=" + moduleId +
				", name='" + name + '\'' +
				", price=" + price +
				", shopId=" + shopId +
				", status=" + status +
				", categoryId=" + categoryId +
				", unit='" + unit + '\'' +
				", goodsType=" + goodsTypeId +
				", ratio=" + ratio +
				", ratioPrice=" + ratioPrice +
				'}';
	}
}