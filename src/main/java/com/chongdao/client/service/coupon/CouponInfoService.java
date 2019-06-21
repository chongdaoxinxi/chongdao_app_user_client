package com.chongdao.client.service.coupon;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.coupon.CouponInfo;

public interface CouponInfoService {

    /**
     * 添加优惠券
     * @param couponInfo
     * @return
     */
    ResultResponse add(CouponInfo couponInfo);

    /**
     * 获取分类
     * @param categoryId 3限商品 4限服务
     * @return
     */
    ResultResponse getCategory(Integer categoryId);
}
