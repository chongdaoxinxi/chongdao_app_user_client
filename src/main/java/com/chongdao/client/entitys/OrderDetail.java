package com.chongdao.client.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/** 
 * @Author onlineS
 * @Description 订单明细
 * @Date 17:44 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_detail")
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private Integer orderNo;
	private Integer count;
	private Integer goodId;
	private String name;
	private BigDecimal price;
	private String icon;
	private BigDecimal currentPrice;
	private Date createTime;
	private Date updateTime;


	@Override
	public String toString() {
		return "OrderDetail{" +
				"id=" + id +
				", orderNo=" + orderNo +
				", count=" + count +
				", goodId=" + goodId +
				", name='" + name + '\'' +
				", price=" + price +
				", icon='" + icon + '\'' +
				", currentPrice=" + currentPrice +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}