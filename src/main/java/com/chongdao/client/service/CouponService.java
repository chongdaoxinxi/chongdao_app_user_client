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

    /**
     * 查询已领取优惠券
     * @param userId
     * @param shopId
     * @return
     */
    ResultResponse receiveCouponComplete(Integer userId, Integer shopId);


    /**
     * 查询配送券列表
     * @param userId
     * @param param
     * @return
     */
    ResultResponse getCardServiceList(Integer userId,  String param);




    //---------------------------------------------------------- 商户端 -----------------------------------------------

    /**
     * 添加优惠券
     * @param shopId
     * @param couponVO
     * @param type
     */
    ResultResponse save(Integer shopId, CouponVO couponVO, Integer type);

    /**
     * 优惠券状态（上下架、删除）
     * @param couponId
     */
    ResultResponse updateCouponStatusById(Integer couponId, Integer status);


    /**
     * 根据shopId查找满减优惠券
     * @param shopId
     * @return
     */
    ResultResponse<List<CouponVO>> findByShopId(Integer shopId, Integer type);
}
