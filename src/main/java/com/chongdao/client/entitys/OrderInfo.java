package com.chongdao.client.entitys;

import lombok.Getter;
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
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer orderNo;

    private Integer userId;

    private Integer shopId;

    private BigDecimal goodsPrice;

    private BigDecimal servicePrice;

    private BigDecimal totalDiscount;

    private String payment;

    private Byte follow;

    private Integer serviceType;

    private Integer cardId;

    private String remark;

    private Date createTime;

    private Date deliverTime;

    private Date paymentTime;

    private Byte paymentType;

    private Date shopReceiveTime;

    private Integer expressId;

    private Date expressReceiveTime;

    private Date expressFinishTime;

    private Integer orderStatus;

    private BigDecimal reward;

    public OrderInfo(Integer id, Integer orderNo, Integer userId, Integer shopId, BigDecimal goodsPrice, BigDecimal servicePrice, BigDecimal totalDiscount, String payment, Byte follow, Integer serviceType, Integer cardId, String remark, Date createTime, Date deliverTime, Date paymentTime, Byte paymentType, Date shopReceiveTime, Integer expressId, Date expressReceiveTime, Date expressFinishTime, Integer orderStatus, BigDecimal reward) {
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
        this.paymentTime = paymentTime;
        this.paymentType = paymentType;
        this.shopReceiveTime = shopReceiveTime;
        this.expressId = expressId;
        this.expressReceiveTime = expressReceiveTime;
        this.expressFinishTime = expressFinishTime;
        this.orderStatus = orderStatus;
        this.reward = reward;
    }


}