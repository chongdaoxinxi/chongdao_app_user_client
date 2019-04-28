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
	@Column(name="order_id")
	private Integer orderId;
	@Column(name = "order_no")
	private String orderNo;
	@Column(name="user_address_id")
	private Integer userAddressId;
	@Column(name="user_name")
	private String userName;
	private String phone;
	private String location;//定位地址
	private String address;//详细地址
	private Double lat;
	private Double lng;
	private Integer type;//1:接宠;2:送宠;

	@Override
	public String toString() {
		return "OrderAddress{" +
				"id=" + id +
				", orderId=" + orderId +
				", orderNo='" + orderNo + '\'' +
				", userAddressId=" + userAddressId +
				", userName='" + userName + '\'' +
				", phone='" + phone + '\'' +
				", location='" + location + '\'' +
				", address='" + address + '\'' +
				", lat=" + lat +
				", lng=" + lng +
				", type=" + type +
				'}';
	}
}