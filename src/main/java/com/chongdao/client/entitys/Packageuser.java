package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 礼包用户关联表
 * @Date 9:04 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "package_user")
public class Packageuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer userId;
	private Integer packageId;
	private Integer count;
	private Integer status;

	private Date createTime;
	private Date updateTime;

	//废弃字段
	private Integer eventid;
}