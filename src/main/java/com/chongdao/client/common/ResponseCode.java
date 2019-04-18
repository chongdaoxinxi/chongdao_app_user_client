package com.chongdao.client.common;

import lombok.Getter;

/**
 * 响应状态码
 */
@Getter
public enum ResponseCode {
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"ERROR"),
    UNSUPPORTED_OPERATION(5003,"该订单已被接单"),
    UNSUPPORTED_AREA(5004,"该地区暂未开放哦~"),
    ERROR_REFUND(5004,"该订单已经正在处理退款了~"),
    NOT_FOUND_SHOP(6004,"该地区暂无店铺~"),
    PARAM_ERROR(5005, "参数有误"),

    UP_COUPON(0,"上架满减优惠券"),
    //逻辑删除
    DELETE_COUPON(-1, "删除满减优惠券"),
    DOWN_COUPON(1, "下架满减优惠券"),

    NOT_FOUND(5006,"满减优惠券不存在"),
    //优惠券类别
    COUPON_FULL(2,"满减优惠券"),
    COUPON_COMMON(3,"通用优惠券"),
    COUPON_ACTIVE(0,"店铺满减优惠"),

    COUPON_SERVICE(11, "服务优惠券"),
    COUPON_GOODS(1, "商品优惠券"),
    COUPON_GOODS_FULL(0, "店铺满减"),

    RECEIVED_COUPON_CARD(5007,"您已领取过该优惠券，不能再次领取"),


    ;

    private final int status;

    private final String msg;

    ResponseCode(int status,String msg) {
        this.status = status;
        this.msg = msg;
    }
}
