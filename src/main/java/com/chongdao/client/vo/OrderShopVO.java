package com.chongdao.client.vo;

import com.chongdao.client.entitys.UserAddress;
import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/18
 * @Version 1.0
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderShopVO {
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
    private BigDecimal discountTotalPrice = BigDecimal.ZERO;//打折扣费总计
    private BigDecimal goodsTotalPrice = BigDecimal.ZERO;
    private Integer quantity;
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
    private Double discount = 0.0d;//折扣
    private Double reDiscount = 0.0d;
    private List<CpnThresholdRule> couponInfoList;
    //优惠价（不包含满减）
    private BigDecimal couponPrice = BigDecimal.ZERO;//非满减优惠
    //优惠券名称
    private String couponName;//非满减优惠名称
    //满减名称
    private String fullCouponName;//满减优惠名称
    //满减 4
    private BigDecimal fullCouponPrice = BigDecimal.ZERO;
    private BigDecimal serviceCouponPrice = BigDecimal.ZERO;
    private BigDecimal goodsCouponPrice = BigDecimal.ZERO;//商品满减
    //商家管理（商品数目）
    private Integer goodsCount = 0;
    private Double expressLng;
    private Double expressLat;
    private Double userLng;
    private Double userLat;
    private Double shopLng;
    private Double shopLat;
    private Integer petCount;
    private String petIds;
    private BigDecimal insurancePrice;
    //该字段用于区分优惠券使用场景
    private String categoryId;

    private BigDecimal deduct;
    private BigDecimal realInMoney;
}
