package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="user_address")
public class UserAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	@Column(name="user_id")
	private Integer userId;//关联用户表id
	@Column(name="user_name")
	private String userName;//收货人姓名
	private String location;//定位地址
	private String address;//详细地址
	private String phone;//联系电话
	private Double lat;//经度
	private Double lng;//纬度
	@Column(name = "is_default_address")
	private Integer isDefaultAddress;//是否默认地址
	private Integer status;//-1:失效;1:生效
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	@Override
	public String toString() {
		return "UserAddress{" +
				"id=" + id +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", address='" + address + '\'' +
				", phone='" + phone + '\'' +
				", location='" + location + '\'' +
				", lat=" + lat +
				", lng=" + lng +
				", isDefaultAddress=" + isDefaultAddress +
				", status=" + status +
				'}';
	}
}