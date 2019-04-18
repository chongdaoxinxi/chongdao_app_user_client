package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the log_coupon database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "log_coupon")
public class LogCoupon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	@Column(name="coupon_id")
	private Integer couponId;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="share_id")
	private Integer shareId;

	private Integer type;

	@Column(name="user_id")
	private Integer userId;

	@Override
	public String toString() {
		return "LogCoupon{" +
				"id=" + id +
				", couponId=" + couponId +
				", orderNo='" + orderNo + '\'' +
				", shareId=" + shareId +
				", type=" + type +
				", userId=" + userId +
				'}';
	}
}