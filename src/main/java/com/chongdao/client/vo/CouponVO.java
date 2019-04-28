package com.chongdao.client.vo;

import com.chongdao.client.enums.ResultEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponVO {

    private Integer id;

    private Integer shopId;

    private Integer categoryId;

    private String couponName;

    private BigDecimal fullPrice;

    //减金额
    private BigDecimal decreasePrice;

    //满减优惠券状态 -1删除，0上架，1下架（默认为0）
    private Integer status = ResultEnum.UP_COUPON.getStatus();

    //有效时间
    private String activeTime;

    //失效时间
    private String missTime;

    //0:满减券(店铺活动),3:优惠券,2满减优惠券
    private Integer type;

    //优惠券数量
    private Integer usedCouponCount;

    //已领取优惠券数量
    private Integer receiveCouponCount;





}
