package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @Author onlineS
 * @Description 订单收货地址
 * @Date 17:43 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_address")
public class OrderAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String address;
	private Double lat;
	private Double lng;
	@Column(name="order_id")
	private Integer orderId;
	private String phone;
	private Integer type;
	@Column(name="user_name")
	private String userName;

	@Override
	public String toString() {
		return "OrderAddress{" +
				"id=" + id +
				", address='" + address + '\'' +
				", lat=" + lat +
				", lng=" + lng +
				", orderId=" + orderId +
				", phone='" + phone + '\'' +
				", type=" + type +
				", userName='" + userName + '\'' +
				'}';
	}
}