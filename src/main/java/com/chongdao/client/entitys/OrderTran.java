package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ordertrans database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "ordertrans")
public class OrderTran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	
	private Integer adtorderid;

	private BigDecimal amount;

	private String comment;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private BigDecimal goodsprice;

	@Column(name="order_id")
	private Integer orderId;

	private BigDecimal platamount;

	private BigDecimal payoffamount;

	private BigDecimal purseamount;
	
	private BigDecimal goodsdiscount;
	
	private BigDecimal servicediscount;
	
	private Integer rate;

	private BigDecimal serviceprice;

	@Column(name="shop_id")
	private int shopId;

	private BigDecimal shopamount;

	private Integer status;

	private BigDecimal totalprice;
	
	private BigDecimal totaldiscount;

	private Integer type;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Column(name="user_id")
	private Integer userId;

	private Integer version;

	@Override
	public String toString() {
		return "OrderTran [id=" + id + ", adtorderid=" + adtorderid + ", amount=" + amount + ", comment=" + comment
				+ ", createby=" + createby + ", createdate=" + createdate + ", goodsprice=" + goodsprice + ", orderId="
				+ orderId + ", platamount=" + platamount + ", payoffamount=" + payoffamount + ", purseamount="
				+ purseamount + ", goodsdiscount=" + goodsdiscount + ", servicediscount=" + servicediscount + ", rate="
				+ rate + ", serviceprice=" + serviceprice + ", shopId=" + shopId + ", shopamount=" + shopamount
				+ ", status=" + status + ", totalprice=" + totalprice + ", totaldiscount=" + totaldiscount + ", type="
				+ type + ", updateby=" + updateby + ", updatedate=" + updatedate + ", userId=" + userId + ", version="
				+ version + "]";
	}
}