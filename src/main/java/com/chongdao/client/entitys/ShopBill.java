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
 * @Author onlineS
 * @Description 商家金额交易
 * @Date 9:11 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="shop_bill")
public class ShopBill extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="user_id")
	private Integer userId;
	@Column(name="shop_id")
	private Integer shopId;
	@Column(name="order_id")
	private Integer orderId;
	private BigDecimal price;
	private String note;
	private Integer type;//1:客户订单, 2:订单退款, 3:店铺提现
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;

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