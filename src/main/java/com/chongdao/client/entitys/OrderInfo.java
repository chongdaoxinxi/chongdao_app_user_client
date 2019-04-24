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
 * @Description 订单信息
 * @Date 17:45 2019/4/18
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "order_no")
	private String orderNo;//订单号, 唯一
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "shop_id")
	private Integer shopId;
	@Column(name = "express_id")
	private Integer expressId;//配送员id
	private String note;//备注
	private Integer follow;//是否跟车
	@Column(name = "order_status")
	private Integer orderStatus;//订单状态-2,-1,1,2,3,4,5,6,7,8,9,10,11,12,13
	private Integer reward;//订单返现活动奖金

	@Column(name = "pet_amount")
	private Integer petAmount;//宠物数量
	@Column(name = "goods_price")
	private BigDecimal goodsPrice;//商品价格
	private Integer goodsDiscard;//商品优惠券id
	private BigDecimal goodsDiscount;//商品优惠券优惠价格
	@Column(name = "service_price")
	private BigDecimal servicePrice;//服务价格
	private Integer serviceDiscard;//服务优惠券id
	private BigDecimal serviceDiscount;//服务优惠券优惠价格
	@Column(name = "discount_money")
	private BigDecimal discountMoney;//优惠金额
	@Column(name = "total_price")
	private BigDecimal totalPrice;//总金额(计算优惠后)
	@Column(name = "pay_user")
	private String payUser;//付款用户

	@Column(name = "deliver_add")
	private Integer deliverAdd;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deliver_time")
	private Date deliverTime;
	@Column(name = "receive_add")
	private Integer receiveAdd;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "receive_time")
	private Date receiveTime;

	@Column(name = "is_service")
	private Integer isService;
	@Column(name = "refuse_note")
	private String refuseNote;
	@Column(name = "shop_note")
	private String shopNote;
	@Column(name = "shop_note_img")
	private String shopNoteImg;

	private Date createTime;


	@Override
	public String toString() {
		return "OrderInfo{" +
				"id=" + id +
				", orderNo='" + orderNo + '\'' +
				", userId=" + userId +
				", shopId=" + shopId +
				", expressId=" + expressId +
				", note='" + note + '\'' +
				", follow=" + follow +
				", orderStatus=" + orderStatus +
				", reward=" + reward +
				", petAmount=" + petAmount +
				", goodsPrice=" + goodsPrice +
				", goodsDiscard=" + goodsDiscard +
				", goodsDiscount=" + goodsDiscount +
				", servicePrice=" + servicePrice +
				", serviceDiscard=" + serviceDiscard +
				", serviceDiscount=" + serviceDiscount +
				", discountMoney=" + discountMoney +
				", totalPrice=" + totalPrice +
				", payUser='" + payUser + '\'' +
				", deliverAdd=" + deliverAdd +
				", deliverTime=" + deliverTime +
				", receiveAdd=" + receiveAdd +
				", receiveTime=" + receiveTime +
				", isService=" + isService +
				", refuseNote='" + refuseNote + '\'' +
				", shopNote='" + shopNote + '\'' +
				", shopNoteImg='" + shopNoteImg + '\'' +
				", createTime=" + createTime +
				'}';
	}
}