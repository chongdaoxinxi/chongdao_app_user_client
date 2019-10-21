package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
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
 * @Description 商家金额交易
 * @Date 9:11 2019/4/19
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
	private Integer type;//1:订单消费, 2:订单退款, 3:店铺提现, 4:医疗费用订单, 5: 追加订单
	private Date createTime;
}