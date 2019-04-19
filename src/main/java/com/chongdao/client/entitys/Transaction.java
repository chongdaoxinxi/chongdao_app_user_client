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
 * @Description 订单交易记录(充值)
 * @Date 9:25 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="transaction")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="user_id")
	private Integer userId;
	@Column(name="order_id")
	private Integer orderId;
	private String orderno;
	@Column(name="shop_id")
	private Integer shopId;
	private Integer type;

	private String transactionid;//交易id
	private String comment;
	private String result;
	@Column(name="result_code")
	private String resultCode;

	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	//废弃字段
	private BigDecimal amount;

	@Override
	public String toString() {
		return "Transaction{" +
				"id=" + id +
				", amount=" + amount +
				", comment='" + comment + '\'' +
				", createby=" + createby +
				", createdate=" + createdate +
				", orderId=" + orderId +
				", orderno='" + orderno + '\'' +
				", result='" + result + '\'' +
				", resultCode='" + resultCode + '\'' +
				", shopId=" + shopId +
				", transactionid='" + transactionid + '\'' +
				", type=" + type +
				", updateby=" + updateby +
				", updatedate=" + updatedate +
				", userId=" + userId +
				", version=" + version +
				'}';
	}
}