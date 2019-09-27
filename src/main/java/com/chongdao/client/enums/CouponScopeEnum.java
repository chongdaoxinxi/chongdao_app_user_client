package com.chongdao.client.enums;

/**
 * @author fenglong
 * @date 2019-09-27 11:07
 */
public enum  CouponScopeEnum {


    ALL(1,"全场通用"),
    LIMITED_CATEGORY(2,"限品类"),
    LIMITED_GOODS(3, "限商品"),
    LIMITED_SERVICE(4, "限服务"),
    LIMITED_ONE_WAY(5, "限配送单程"),
    LIMITED_TWO_WAY(6, "限配送双程"),
    LIMITED_SERVICE_ONE_WAY(7, "限服务配送单程"),
    LIMITED_SERVICE_TWO_WAY(8, "限服务配送双程"),
    LIMITED_GOODS_ONE_WAY(7, "限商品配送单程"),
    LIMITED_GOODS_TWO_WAY(8, "限商品配送双程"),

    ;



    /** 错误码 */
    private Integer status;

    /** 错误信息 */
    private String message;

    CouponScopeEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
