package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @Author onlineS
 * @Description 退款信息
 * @Date 9:00 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_refund")
public class OrderRefund implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="order_id")
	private Integer orderId;//订单id
	private String note;//退款信息
	private Integer type;//1: 用户, 2:商家
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Override
	public String toString() {
		return "OrderRefund{" +
				"id=" + id +
				", createdate=" + createdate +
				", note='" + note + '\'' +
				", orderId=" + orderId +
				", type=" + type +
				'}';
	}
}