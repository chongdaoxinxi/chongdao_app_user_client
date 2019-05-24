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

	private String icon; //图片

	private BigDecimal price;

	private Double discount = 0.0D;

	private String unit; //计量单位

	private String des; //商品介绍

	private Integer goodTypeId; //所属分类id

	private Integer shopId;

	//提高系数默认为1
	private Double ratio = 1.0D;

	private BigDecimal ratioPrice = price.multiply(new BigDecimal(ratio)).setScale(2);

	private Integer stock;

	private Date createTime;

	private Date updateTime;

	private Byte status; //-1: 删除, 0:下架, 1:上架

	private Integer moduleId; //所属模块id

	private Integer categoryId; //所属类别id

	private Integer sales;

	@Transient
	private String categoryName;

	@Transient
	private Integer type;

	@Transient
	private String couponName;

	@Transient
	private Integer couponId;

	public Good(Integer id, String name, String icon, BigDecimal price, Double discount, String unit, String des, Integer goodTypeId,
				Integer shopId, Double ratio, BigDecimal ratioPrice, Integer stock, Date createTime, Date updateTime, Byte status, Integer moduleId, Integer categoryId,Integer sales) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.price = price;
		this.discount = discount;
		this.unit = unit;
		this.des = des;
		this.goodTypeId = goodTypeId;
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
				", goodsType=" + goodTypeId +
				", ratio=" + ratio +
				", ratioPrice=" + ratioPrice +
				'}';
	}
}