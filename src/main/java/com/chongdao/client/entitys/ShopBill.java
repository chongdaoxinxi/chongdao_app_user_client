package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the shop_bill database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name="shop_bill")
public class ShopBill extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

	private String note;

	@Column(name="order_id")
	private Integer orderId;

	private BigDecimal price;

	@Column(name="shop_id")
	private Integer shopId;

	private Integer type;

	@Column(name="user_id")
	private Integer userId;

	@Override
	public String toString() {
		return "ShopBill{" +
				"id=" + id +
				", createdate=" + createdate +
				", note='" + note + '\'' +
				", orderId=" + orderId +
				", price=" + price +
				", shopId=" + shopId +
				", type=" + type +
				", userId=" + userId +
				'}';
	}
}