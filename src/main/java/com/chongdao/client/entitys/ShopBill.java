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
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer shopId;
	private Integer orderId;
	private BigDecimal price;
	private String note;
	private Integer type;//1:客户订单, 2:订单退款, 3:店铺提现
	private Date createTime;
}