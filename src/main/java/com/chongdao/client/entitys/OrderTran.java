package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author onlineS
 * @Description 订单金额明细
 * @Date 9:01 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_trans")
public class OrderTran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer orderId;
	private String comment;//
	private Date createTime;
}