package com.chongdao.client.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String orderNo;

    private Integer userId;

    private Integer shopId;

    private BigDecimal goodsPrice;

    private BigDecimal servicePrice;

    private BigDecimal totalDiscount;

    private BigDecimal payment;

    private Byte follow;

    private Integer serviceType;

    private Integer cardId;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Date deliverTime;

    private Date receiveTime;

    private Date paymentTime;

    private Integer paymentType;

    private Date shopReceiveTime;

    private Integer expressId;

    private Date expressReceiveTime;

    private Date expressFinishTime;

    private Integer orderStatus;

    private Integer addressId;

    private BigDecimal reward;

    private Integer isService;

    private Integer couponId;

    public OrderInfo(Integer id, String orderNo, Integer userId, Integer shopId, BigDecimal goodsPrice, BigDecimal servicePrice, BigDecimal totalDiscount,
                     BigDecimal payment, Byte follow, Integer serviceType, Integer cardId, String remark, Date createTime, Date deliverTime,Date receiveTime, Date paymentTime,
                     Integer paymentType, Date shopReceiveTime, Integer expressId, Date expressReceiveTime, Date expressFinishTime, Integer orderStatus,Integer addressId, BigDecimal reward,
                     Integer isService, Integer couponId) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.shopId = shopId;
        this.goodsPrice = goodsPrice;
        this.servicePrice = servicePrice;
        this.totalDiscount = totalDiscount;
        this.payment = payment;
        this.follow = follow;
        this.serviceType = serviceType;
        this.cardId = cardId;
        this.remark = remark;
        this.createTime = createTime;
        this.deliverTime = deliverTime;
        this.receiveTime = receiveTime;
        this.paymentTime = paymentTime;
        this.paymentType = paymentType;
        this.shopReceiveTime = shopReceiveTime;
        this.expressId = expressId;
        this.expressReceiveTime = expressReceiveTime;
        this.expressFinishTime = expressFinishTime;
        this.orderStatus = orderStatus;
        this.reward = reward;
        this.addressId = addressId;
        this.isService = isService;
        this.couponId = couponId;
    }


}