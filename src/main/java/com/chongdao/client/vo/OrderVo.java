package com.chongdao.client.vo;

import com.chongdao.client.entitys.UserAddress;
import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderVo {
    private Integer id;

    private String orderNo;

    private Integer userId;

    private Integer ShopId;

    private BigDecimal goodsPrice = BigDecimal.ZERO;

    private BigDecimal servicePrice = BigDecimal.ZERO;

    private BigDecimal totalDiscount = BigDecimal.ZERO;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    private BigDecimal payment = BigDecimal.ZERO;

    private Byte follow ;

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

    private String shopPhone;

    private String shopLogo;

    private String goodsName;

    private BigDecimal discountPrice = BigDecimal.ZERO;

    private BigDecimal goodsTotalPrice = BigDecimal.ZERO;

    private Integer quantity;

    //private List<CouponVO> couponList;

    private Integer couponId;

    private Integer serviceCouponCount;

    private Integer goodsCouponCount;

    private String deliverAddressName;

    private String receiveAddressName;

    private String areaCode;

    private String phone;

    private String username;

    //订单商品Vo
    private List<OrderGoodsVo> orderGoodsVoList;

    private UserAddress userAddress;

    //配送员姓名
    private String expressName;
    private String expressPhone;

    private List<OrderDetailVO> orderDetailVOList;

    private Double discount = 0.0d;

    private Double reDiscount = 0.0d;

    private List<CpnThresholdRule> couponInfoList;

    //优惠价（不包含满减）
    private BigDecimal couponPrice = BigDecimal.ZERO;
    //优惠券名称
    private String couponName;
    //满减名称
    private String fullCouponName;
    //满减 4
    private BigDecimal fullCouponPrice = BigDecimal.ZERO;
    private BigDecimal serviceCouponPrice = BigDecimal.ZERO;
    private BigDecimal goodsCouponPrice = BigDecimal.ZERO;

    //商家管理（商品数目）
    private Integer goodsCount = 0;

}