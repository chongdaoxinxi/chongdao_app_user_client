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
 * @Description 订单金额明细
 * @Date 9:01 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "ordertrans")
public class OrderTran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="user_id")
	private Integer userId;
	@Column(name="shop_id")
	private int shopId;
	@Column(name="order_id")
	private Integer orderId;
	private Integer adtorderid;
	private String comment;
	private Integer rate;
	private Integer type;
	private Integer status;

	private BigDecimal amount;
	private BigDecimal goodsprice;
	private BigDecimal goodsdiscount;
	private BigDecimal serviceprice;
	private BigDecimal servicediscount;
	private BigDecimal totalprice;
	private BigDecimal totaldiscount;

	private BigDecimal purseamount;
	private BigDecimal shopamount;
	private BigDecimal platamount;
	private BigDecimal payoffamount;

	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

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