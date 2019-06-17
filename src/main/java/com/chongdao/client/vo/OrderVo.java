package com.chongdao.client.vo;

import com.chongdao.client.entitys.UserAddress;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderVo {

    private String orderNo;

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

    private Date shopFinishTime;

    private Integer expressId;

    private Date expressReceiveTime;

    private Date expressFinishTime;

    private Integer orderStatus;

    private BigDecimal reward;

    private Integer receiveAddressId;

    private Integer deliverAddressId;

    private Integer isService;

    private String shopName;

    private String shopLogo;

    private String goodsName;

    private BigDecimal discountPrice;

    private BigDecimal GoodsTotalPrice;

    private Integer quantity;

    private List<CouponVO> couponList;

    private Integer serviceCouponCount;

    private Integer goodsCouponCount;

    private String deliverAddressName;

    private String receiveAddressName;

    private String areaCode;

    //订单商品Vo
    private List<OrderGoodsVo> orderGoodsVoList;

    private UserAddress userAddress;



}
