package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/** 
 * @Author onlineS
 * @Description(暂时废弃)
 * @Date 17:42 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
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