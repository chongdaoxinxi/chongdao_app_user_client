package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="transaction")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private BigDecimal amount;

	private String comment;

	private Integer createby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	@Column(name="order_id")
	private Integer orderId;

	private String orderno;

	private String result;

	@Column(name="result_code")
	private String resultCode;

	@Column(name="shop_id")
	private Integer shopId;

	private String transactionid;

	private Integer type;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Column(name="user_id")
	private Integer userId;

	private Integer version;

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