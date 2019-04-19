package com.chongdao.client.entitys;

import com.chongdao.client.common.PageParams;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/** 
 * @Author onlineS
 * @Description 订单评价
 * @Date 17:44 2019/4/18
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "order_eval")
public class OrderEval extends PageParams implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer grade;
	private String img;
	@Column(name="order_id")
	private Integer orderId;
	@Column(name="shop_id")
	private Integer shopId;
	private Integer status;
	@Column(name="user_id")
	private Integer userId;
	private String orderNo;

	@Override
	public String toString() {
		return "OrderEval{" +
				"id=" + id +
				", content='" + content + '\'' +
				", createdate=" + createdate +
				", grade=" + grade +
				", img='" + img + '\'' +
				", orderId=" + orderId +
				", shopId=" + shopId +
				", status=" + status +
				", userId=" + userId +
				", orderNo='" + orderNo + '\'' +
				'}';
	}
}