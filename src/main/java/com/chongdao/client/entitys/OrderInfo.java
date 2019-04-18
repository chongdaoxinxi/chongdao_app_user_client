package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the order_info database table.
 * 
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_info")
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Column(name="deliver_add")
	private Integer deliverAdd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deliver_time")
	private Date deliverTime;
	
	private Integer goodsdiscard;
	
	private Integer servicediscard;
	
	private BigDecimal goodsdiscount;
	
	private BigDecimal servicediscount;

	@Column(name="discount_money")
	private BigDecimal discountMoney;

	@Column(name="express_id")
	private Integer expressId;

	@Column(name="goods_price")
	private BigDecimal goodsPrice;

	@Column(name="is_service")
	private Integer isService;

	private String note;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="order_status")
	private Integer orderStatus;

	@Column(name="pay_user")
	private String payUser;

	@Column(name="pet_amount")
	private Integer petAmount;
	
	private Integer follow;
	
	private BigDecimal payoffamount;

	private BigDecimal purseamount;

	@Column(name="receive_add")
	private Integer receiveAdd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="receive_time")
	private Date receiveTime;

	@Column(name="refuse_note")
	private String refuseNote;

	@Column(name="server_order")
	private Integer serverOrder;

	@Column(name="service_price")
	private BigDecimal servicePrice;

	@Column(name="shop_id")
	private Integer shopId;

	@Column(name="shop_note")
	private String shopNote;

	@Column(name="shop_note_img")
	private String shopNoteImg;

	@Column(name="total_price")
	private BigDecimal totalPrice;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Column(name="user_id")
	private Integer userId;
	private Integer version;
	private Integer courierHasReceive;
	private Integer courierServiceFinish;
	private Integer courierId;
	private Integer couponId;
	private  Integer reward;

	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", createby=" + createby + ", createdate=" + createdate + ", deliverAdd="
				+ deliverAdd + ", deliverTime=" + deliverTime + ", goodsdiscard=" + goodsdiscard + ", servicediscard="
				+ servicediscard + ", goodsdiscount=" + goodsdiscount + ", servicediscount=" + servicediscount
				+ ", discountMoney=" + discountMoney + ", expressId=" + expressId + ", goodsPrice=" + goodsPrice
				+ ", isService=" + isService + ", note=" + note + ", orderNo=" + orderNo + ", orderStatus="
				+ orderStatus + ", payUser=" + payUser + ", petAmount=" + petAmount + ", follow=" + follow
				+ ", payoffamount=" + payoffamount + ", purseamount=" + purseamount + ", receiveAdd=" + receiveAdd
				+ ", receiveTime=" + receiveTime + ", refuseNote=" + refuseNote + ", serverOrder=" + serverOrder
				+ ", servicePrice=" + servicePrice + ", shopId=" + shopId + ", shopNote=" + shopNote + ", shopNoteImg="
				+ shopNoteImg + ", totalPrice=" + totalPrice + ", updateby=" + updateby + ", updatedate=" + updatedate
				+ ", userId=" + userId + ", version=" + version + "]";
	}
}