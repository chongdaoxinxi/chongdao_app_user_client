package com.chongdao.client.vo;

import com.chongdao.client.entitys.Coupon;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderVo {

    private Integer orderNo;

    private Integer userId;

    private Integer ShopId;

    private BigDecimal goodsPrice;

    private BigDecimal servicePrice;

    private BigDecimal totalDiscount;

    private BigDecimal totalPrice;

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

    private BigDecimal reward;

    private Integer AddressId;

    private Integer isService;

    private String shopName;

    private String goodsName;

    private BigDecimal discountPrice;

    private BigDecimal GoodsTotalPrice;

    private Integer quantity;

    private List<CouponVO> couponList;

    private Integer serviceCouponCount;

    private Integer goodsCouponCount;

    //订单明细
    private List<OrderGoodsVo> orderGoodsVoList;


}
