package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 用户分享记录
 * @Date 9:32 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="user_share")
public class UserShare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer userId;
	private Integer maxNum;//分享次数
	private Integer type;//1:分享店铺,2;分享商品;3:分享小程序
	private Integer couponId;//优惠券id包含(配送券)
	//private Integer cardId;//优惠券id(废弃)
	private Date createTime;
	private Date updateTime;
}