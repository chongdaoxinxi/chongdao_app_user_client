package com.chongdao.client.common;


import com.google.common.collect.Sets;

import java.util.Set;

public class Const {

    //收货地址-正常使用
    public static final Integer NORMAL_ADDRESS_STATUS = 1;
    //收货地址-逻辑删除状态
    public static final Integer DELETE_ADDRESS_STATUS = -1;


    /**
     * 商品排序
     */
    public interface goodsListOrderBy{
        //综合排序
        String ARRANGEMENT_KEY = "arrangement";
        String ARRANGEMENT_VALUE = "sales desc" + "," +"price asc";
        //价格排序
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
        //销量排序
        Set<String> SALES_ASC_DESC = Sets.newHashSet("sales_desc","sales_asc");
        //orderBy为空的排序方式
        String DEFAULT_ORDER_BY = "null";


    }

    /**
     * 商品预下单-》配送费用计算常量
     */
    public interface dispatch{
        Integer DISPATCH_FEE = 15;
        //单程
        Integer DISPATCH_NOT_RETIND = 1;
        //双程
        Integer DISPATCH_RETIND = 2;

        //活动计算运费（狗数目）

        Integer ACTIVITY_PET_FARE = 1;
    }

}
