package com.chongdao.client.common;

import com.chongdao.client.entitys.coupon.CouponInfo;

/**
 * @author fenglong
 * @date 2019-10-17 14:14
 */
public class CouponScopeCommon {

    public static CouponInfo cpnScope(Integer scopeType,CouponInfo couponInfo){
        switch (scopeType) {
            case 1:
                couponInfo.setScopeName("全场通用");
                break;
            case 2:
                couponInfo.setScopeName("限品类");
                break;
            case 3:
                couponInfo.setScopeName("限商品");
                break;
            case 4:
                couponInfo.setScopeName("限服务");
                break;
            case 5:
                couponInfo.setScopeName("限单程");
                break;
            case 6:
                couponInfo.setScopeName("限双程");
                break;
            case 7:
                couponInfo.setScopeName("限服务（单程）");
                break;
            case 8:
                couponInfo.setScopeName("限服务（双程）");
                break;
            case 9:
                couponInfo.setScopeName("限商品（单程）");
                break;
            case 10:
                couponInfo.setScopeName("限商品（双程）");
                break;
        }
        return couponInfo;
    }
}
