package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 用户金额交易记录
 * @Date 9:33 2019/4/19
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="user_trans")
public class UserTrans implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer userId;
	private Integer orderId;
	private String comment;//注释
	private BigDecimal money;
	private Integer type;//1:用户充值;2:订单消费;3:订单退款

	private Date createTime;
	private Date updateTime;

	//当类型为用户消费和订单退款时, 冗余消费商铺的名称
	private String shopName;
}