package com.chongdao.client.common;


public class Const {

    //收货地址-正常使用
    public static final Integer NORMAL_ADDRESS_STATUS = 1;
    //收货地址-逻辑删除状态
    public static final Integer DELETE_ADDRESS_STATUS = -1;

    /**
     * 商品预下单-》配送费用计算常量
     */
    public interface dispatch{
        public static final Integer DISPATCH_FEE = 15;
        //单程
        public static final Integer DISPATCH_NOT_RETIND = 1;
        //双程
        public static final Integer DISPATCH_RETIND = 2;

        //活动计算运费（狗数目）

        public static final Integer ACTIVITY_PET_FARE = 1;
    }

}
