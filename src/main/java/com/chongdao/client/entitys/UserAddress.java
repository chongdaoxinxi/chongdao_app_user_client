package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the user_address database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="user_address")
public class UserAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private String address;

	private Double lat;

	private Double lng;

	private String location;

	private String phone;

	private Integer status;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="user_name")
	private String userName;

	@Column(name = "default_address")
	private Integer defaultAddress;

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