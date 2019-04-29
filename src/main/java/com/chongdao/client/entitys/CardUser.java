package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 优惠券用户关联表
 * @Date 17:21 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class CardUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer cardId;//优惠券id
	private Integer userId;//用户id
	private Integer count;
	private Integer status;

	private Date createTime;
	private Date updateTime;

	private Integer shopId;
	private Integer couponId;



}