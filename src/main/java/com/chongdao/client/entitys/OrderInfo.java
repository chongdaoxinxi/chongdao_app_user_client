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
@Table(name = "order_info")
public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(name="order_no")
	private String orderNo;//订单号, 唯一
	@Column(name="user_id")
	private Integer userId;
	@Column(name="shop_id")
	private Integer shopId;
	@Column(name="express_id")
	private Integer expressId;//配送员id
	private String note;//备注
	private Integer follow;//是否跟车
	@Column(name="order_status")
	private Integer orderStatus;//订单状态-2,-1,1,2,3,4,5,6,7,8,9,10,11,12,13
	private Integer reward;//订单返现活动奖金

	@Column(name="pet_amount")
	private Integer petAmount;//宠物数量
	@Column(name="goods_price")
	private BigDecimal goodsPrice;//商品价格
	private Integer goodsdiscard;//商品优惠券id
	private BigDecimal goodsdiscount;//商品优惠券优惠价格
	@Column(name="service_price")
	private BigDecimal servicePrice;//服务价格
	private Integer servicediscard;//服务优惠券id
	private BigDecimal servicediscount;//服务优惠券优惠价格
	@Column(name="discount_money")
	private BigDecimal discountMoney;//优惠金额
	@Column(name="total_price")
	private BigDecimal totalPrice;//总金额(计算优惠后)
	@Column(name="pay_user")
	private String payUser;//付款用户

	@Column(name="deliver_add")
	private Integer deliverAdd;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deliver_time")
	private Date deliverTime;
	@Column(name="receive_add")
	private Integer receiveAdd;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="receive_time")
	private Date receiveTime;

	@Column(name="is_service")
	private Integer isService;
	@Column(name="refuse_note")
	private String refuseNote;
	@Column(name="shop_note")
	private String shopNote;
	@Column(name="shop_note_img")
	private String shopNoteImg;

	private Integer version;
	private Integer createby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer updateby;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedate;

	// 暂时废弃字段
	@Column(name="server_order")
	private Integer serverOrder;
	private BigDecimal payoffamount;
	private BigDecimal purseamount;
	private Integer courierId;
	private Integer courierHasReceive;
	private Integer courierServiceFinish;
	private Integer couponId;

	@Override
	public String toString() {
		return "OrderInfo [id=" + id + ", createby=" + createby + ", createdate=" + createdate + ", deliverAdd="
				+ deliverAdd + ", deliverTime=" + deliverTime + ", goodsdiscard=" + goodsdiscard + ", servicediscard="
				+ servicediscard + ", goodsdiscount=" + goodsdiscount + ", servicediscount=" + servicediscount
				+ ", discountMoney=" + discountMoney + ", expressId=" + expressId + ", goodsPrice=" + goodsPrice
				+ ", isService=" + isService + ", note=" + note + ", orderNo=" + orderNo + ", orderStatus="
				+ orderStatus + ", payUser=" + payUser + ", petAmount=" + petAmount + ", follow=" + follow
				+ ", payoffamount=" + payoffamount + ", purseamount=" + purseamount + ", receiveAdd=" + receiveAdd
				+ ", receiveTime=" + receiveTime + ", refuseNote=" + refuseNote + ", serverOrder=" + serverOrder
				+ ", servicePrice=" + servicePrice + ", shopId=" + shopId + ", shopNote=" + shopNote + ", shopNoteImg="
				+ shopNoteImg + ", totalPrice=" + totalPrice + ", updateby=" + updateby + ", updatedate=" + updatedate
				+ ", userId=" + userId + ", version=" + version + "]";
	}
}