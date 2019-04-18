package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the card database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="card")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String comment;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;

	private String name;

	private String note;

	private String shopids;
	
	private String areaids;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;
	
	private BigDecimal price;
	
	private BigDecimal displayprice;

	private Integer status;
	
	private Integer type;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;

	private Integer couponId;

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}


	@Override
	public String toString() {
		return "Card [id=" + id + ", comment=" + comment + ", createby=" + createby + ", createdate=" + createdate
				+ ", enddate=" + enddate + ", name=" + name + ", note=" + note + ", shopids=" + shopids + ", areaids="
				+ areaids + ", startdate=" + startdate + ", price=" + price + ", displayprice=" + displayprice
				+ ", status=" + status + ", type=" + type + ", updateby=" + updateby + ", updatedate=" + updatedate
				+ ", version=" + version + "]";
	}
	
}