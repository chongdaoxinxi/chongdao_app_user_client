package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the order_detail database table.
 * 
 */
@Entity
@Setter
@NoArgsConstructor
@Getter
@Table(name = "order_detail")
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;

	private Integer count;

	private Double discount;

	@Column(name="goods_name")
	private String goodsName;

	private String icon;

	@Column(name="module_id")
	private Integer moduleId;

	@Column(name="old_id")
	private Integer oldId;

	@Column(name="order_id")
	private Integer orderId;

	private BigDecimal price;

	@Column(name="re_order_id")
	private Integer reOrderId;

	@Column(name="shop_id")
	private Integer shopId;

	private Integer type;

	@Column(name="type_id")
	private Integer typeId;

	private String unit;

	private String orderNoRe;

	private Integer status;

	@Override
	public String toString() {
		return "OrderDetail{" +
				"id=" + id +
				", count=" + count +
				", discount=" + discount +
				", goodsName='" + goodsName + '\'' +
				", icon='" + icon + '\'' +
				", moduleId=" + moduleId +
				", oldId=" + oldId +
				", orderId=" + orderId +
				", price=" + price +
				", reOrderId=" + reOrderId +
				", shopId=" + shopId +
				", type=" + type +
				", typeId=" + typeId +
				", unit='" + unit + '\'' +
				", orderNoRe='" + orderNoRe + '\'' +
				", status=" + status +
				'}';
	}
}