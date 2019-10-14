package com.chongdao.client.common;


import com.google.common.collect.Sets;
import java.util.Set;

public class Const {

    //收货地址-正常使用
    public static final Integer NORMAL_ADDRESS_STATUS = 1;
    //收货地址-逻辑删除状态
    public static final Integer DELETE_ADDRESS_STATUS = -1;

    //双程常量
    public static final Integer DUAL = 0;
    //单程常量
    public static final Integer SIGNLE = 1;




    /**
     * 购物车
     */
    public interface Cart{
        Byte CHECKED = 1;
        Byte UN_CHECKED = 2;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    /**
     * 排序
     */
    public interface OrderBy{
        String ASC = "asc";
        String DESC = "desc";
        String LIMIT_3KM_SALES = "(select ifnull(sum(sales),0) from good where shop_id=s.id and round(getDistance(s.lat,s.lng,#{lat},#{lng})) <= 3000)";
        //销量排行（首页）
        String SALES_ORDER_BY_DESC = "sales desc";
        //好评率
        Set<String> FAVORABLE = Sets.newHashSet("grade_desc");
        String DISTANCE = "dis";
        String FAVORABLE_DESC = "grade desc" + "," + DISTANCE;
        //综合排序
        String ARRANGEMENT_KEY = "arrangement";
        String ARRANGEMENT_VALUE_GOODS = "sales desc" + "," +"price asc";
        String ARRANGEMENT_VALUE_SHOP = SALES_ORDER_BY_DESC + "," + FAVORABLE_DESC + " desc" + "," + DISTANCE ;
        String SALES_ORDER_BY_DESC_AND_DISTANCE_3KM= LIMIT_3KM_SALES + DESC + "," + DISTANCE;
        String SALES_ORDER_BY_ASC_AND_DISTANCE_3KM=  LIMIT_3KM_SALES+ ASC + "," + DISTANCE;

        String SALES_ORDER_BY_DESC_AND_SEARCH= "sales" + " "+ DESC + "," + DISTANCE;
        String SALES_ORDER_BY_ASC_AND_SEARCH=  "sales" + " " + ASC + "," + DISTANCE;

        //价格排序
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
        //销量排序（商品）
        Set<String> SALES_ASC_DESC = Sets.newHashSet("sales_desc","sales_asc");
        //orderBy为空的排序方式
        String DEFAULT_ORDER_BY = "null";
    }

    public interface  goodsListProActivities{
        //优惠活动
        //满减、店铺打折、店铺红包
        String DISCOUNT = "0";
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


    /**
     * 支付宝回调状态常量
     */
    public interface  AliPayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }
}
