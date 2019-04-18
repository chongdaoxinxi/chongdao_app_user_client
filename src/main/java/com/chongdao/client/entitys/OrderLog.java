package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the order_log database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "order_log")
public class OrderLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String note;

	@Column(name="order_id")
	private Integer orderId;

	@Column(name="order_status")
	private Integer orderStatus;
	
	private Integer createby;

	private Integer updateby;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	private Integer version;


	@Override
	public String toString() {
		return "OrderLog [id=" + id + ", createdate=" + createdate + ", note=" + note + ", orderId=" + orderId
				+ ", orderStatus=" + orderStatus + ", createby=" + createby + ", updateby=" + updateby + ", updatedate="
				+ updatedate + ", version=" + version + "]";
	}
}