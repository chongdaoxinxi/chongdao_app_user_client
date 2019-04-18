package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the order_refund database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "order_refund")
public class OrderRefund implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String note;

	@Column(name="order_id")
	private Integer orderId;

	private Integer type;

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