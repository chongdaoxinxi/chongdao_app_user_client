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
@Table(name = "order_log")
public class OrderLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="order_id")
	private Integer orderId;//订单id
	private String note;//日志信息
	@Column(name="order_status")
	private Integer orderStatus;//订单所处状态

	private Integer version;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer createby;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	@Override
	public String toString() {
		return "OrderLog [id=" + id + ", createdate=" + createdate + ", note=" + note + ", orderId=" + orderId
				+ ", orderStatus=" + orderStatus + ", createby=" + createby + ", updateby=" + updateby + ", updatedate="
				+ updatedate + ", version=" + version + "]";
	}
}