package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * @Author onlineS
 * @Description 用户地址
 * @Date 9:28 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="user_address")
public class UserAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="user_id")
	private Integer userId;
	@Column(name="user_name")
	private String userName;
	private String address;
	private String phone;
	private String location;
	private Double lat;
	private Double lng;
	@Column(name = "default_address")
	private Integer defaultAddress;
	private Integer status;

	@Override
	public String toString() {
		return "UserAddress{" +
				"id=" + id +
				", address='" + address + '\'' +
				", lat=" + lat +
				", lng=" + lng +
				", location='" + location + '\'' +
				", phone='" + phone + '\'' +
				", status=" + status +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", defaultAddress=" + defaultAddress +
				'}';
	}
}