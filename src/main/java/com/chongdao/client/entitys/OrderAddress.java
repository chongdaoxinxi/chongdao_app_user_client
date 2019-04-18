package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the order_address database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
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