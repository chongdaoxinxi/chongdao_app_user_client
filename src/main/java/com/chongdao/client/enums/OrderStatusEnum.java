package com.chongdao.client.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    ORDER_CLOSE(-3,"订单关闭"),
    CANCELED(-2,"已取消"),
    NO_PAY(-1,"未支付"),
    PAID(1,"已付款"),
    ACCEPTED_ORDER(2,"已接单"),
    EXPRESS_ACCEPTED_ORDER(7, "配送员已接单"),
    EXPRESS_DELIVERY_COMPLETE(10, "配送员配送完成"),
    SHOP_START_SERVICE(11, "商家服务中"),
    SHOP_COMPLETE_SERVICE(12, "商家服务完成"),
    EXPRESS_BACK_DELIVERY(13, "配送员返程配送完成"),
    ORDER_SUCCESS(3,"订单完成"),
    ORDER_EVALUATE(6, "订单已评价"),
    USER_APPLY_REFUND(8, "用户申请退款"),
    REFUND_NOT_AGREE(9, "商家不同意退款"),
    REFUND_AGREE(4, "商家同意退款"),
    AUTO_CONSIDER_REFUND(0, "商家自动拒单"),
    REFUND_COMPLETE(5, "订单退款完成"),

//    IN_SERVICE_ORDER(3,"服务中"),

    ORDER_PRE(1,"预下单"),
    ORDER_CREATE(2,"提交订单"),
    ORDER_SPELL(3,"拼单"),
    RE_ADD_ORDER(4,"追加订单"),
    ADDRESS_NOT_EMPTY(501,"请选择地址后下单"),

    //////////////退款相关//////////////////
    REFUND_PROCESS_APPLY(101, "用户申请退款"),
    REFUND_PROCESS_ACCEPT(102, "商家同意退款"),
    REFUND_PROCESS_REFUSE(103, "商家拒单"),
    REFUND_PROCESS_SUCCESS(104, "退款成功"),

    ORDER_CREATE_ERROR(105, "订单生成失败"),
    CART_EMPTY(106, "购物车为空"),

    ORDER_VALID(107, "请重新添加商品"),

    ;

    private int status;

    private String message;

    OrderStatusEnum(int status, String message){
        this.status = status;
        this.message = message;
    }
}
