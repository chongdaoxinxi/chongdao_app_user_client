package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 订单投诉
 * @Date 9:30 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="usercomplain")
public class UserComplain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private int userId;
	private int orderId;
	private String note;
	private Date updateTime;
	private Date createTime;
}