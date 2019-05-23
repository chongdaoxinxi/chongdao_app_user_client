package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 订单日志
 * @Date 17:45 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
public class OrderLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer orderId;//订单id
	private String orderNo;
	private String note;//日志信息
	private Integer orderStatus;//订单所处状态
	private Integer orderType; //支付类型：1，支付宝 2 微信
	private Date createTime = new Date();
	private Date updateTime = new Date();


}