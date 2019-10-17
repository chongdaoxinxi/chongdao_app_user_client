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


    /**
     * 根据商品id更新相应状态
     * @param cpnId
     * @param state 状态-1 已删除 0待发布 1已发布 2已下架
     * @return
     */
    ResultResponse updateState(Integer cpnId, Integer state);

    /**
     * 查询商家所有优惠券
     * @param shopId
     * @return
     */
    ResultResponse list(Integer shopId,Integer state,Integer cpnType,Integer goodsTypeId);
}
