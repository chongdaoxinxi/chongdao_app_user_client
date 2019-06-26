package com.chongdao.client.common;

/**
 * @author fenglong
 * @date 2019-06-19 14:01
 * 优惠券状态
 */
public class CouponConst {

    /**
     * 优惠券类型
     */
    public static final Integer  CASH_COUPON = 1;     //现金券（红包）
    public static final Integer FULL_REDUCTION = 2;     //满减券
    public static final Integer DISCOUNT = 3;      //折扣
    public static final Integer SHOP_FULL_REDUCTION = 4; //店铺满减
    public static final Integer COMMON = 5; //公益


    /**
     * 门槛规则
     */
    public static final Integer NO_THRESHOLD = 0;  //无门槛
    public static final Integer THRESHOLD = 1;     //有门槛

    /**
     * 适用范围类型 1全场通用 2限品类(暂定) 3限商品 4限服务 5配送单程 6配送双程 7仅限服务（配送单程）
     * 8仅限服务（双程） 9 仅限商品（配送单程） 10仅限商品（配送双程）
     */
    public static final Integer EXPERT = 1;
    public static final Integer LIMITED_CATEGORY = 2;
    public static final Integer LIMITED_GOODS = 3;
    public static final Integer LIMITED_SERVICE = 4;
    public static final Integer SINGLE = 5;
    public static final Integer DOUBLE = 6;
    public static final Integer LIMITED_SERVICE_SINGLE = 7;
    public static final Integer LIMITED_SERVICE_DOUBLE = 8;
    public static final Integer LIMITED_GOODS_SINGLE = 9;
    public static final Integer LIMITED_GOODS_DOUBLE = 10;

}
