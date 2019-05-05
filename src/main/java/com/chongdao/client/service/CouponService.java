package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CouponVO;

import java.util.List;

/**
 * 优惠券功能接口
 */
public interface CouponService {

    /**
     * 商品详情中的优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(1：商品 2: 服务)
     * @return
     */
    ResultResponse<List<CouponVO>> getCouponListByShopIdAndType(Integer userId, Integer shopId, Integer type);

    /**
     * 领取优惠券
     * @param userId
     * @param shopId
     * @param couponId
     * @return
     */
    ResultResponse receiveCoupon(Integer userId, Integer shopId, Integer couponId);
}
