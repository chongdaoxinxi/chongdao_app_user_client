package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 优惠券
 * @Date 17:20 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="card")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String name;
	private String note;//优惠券详情
	private String comment;
	private String shopids;//支持商店(以,连接的字符串)
	private String areaids;//支持区域id(以,连接的字符串)
	private Integer type;
	private BigDecimal displayprice;//优惠金额
	private BigDecimal price;//触发价格(例如满100减10, 触发价格为100)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;
	private Integer couponId;//配送券id(不能重复使用的??)
	private Integer status;//-1删除，0上架，1下架（默认为0）
	private Integer version;//版本控制
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "Card [id=" + id + ", comment=" + comment + ", createby=" + createby + ", createdate=" + createdate
				+ ", enddate=" + enddate + ", name=" + name + ", note=" + note + ", shopids=" + shopids + ", areaids="
				+ areaids + ", startdate=" + startdate + ", price=" + price + ", displayprice=" + displayprice
				+ ", status=" + status + ", type=" + type + ", updateby=" + updateby + ", updatedate=" + updatedate
				+ ", version=" + version + "]";
	}
	
}