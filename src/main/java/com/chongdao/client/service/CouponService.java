package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.coupon.CouponInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券功能接口
 */
public interface CouponService {

    /**
     * 订单优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(0：商品以及服务优惠券 1: 配送优惠券)
     * @param serviceType 服务类型 1.双程 2.单程 3.到店自取
     * @return
     */
    ResultResponse getCouponListByShopIdAndType(Integer userId, String shopId, String categoryId,
                                                BigDecimal totalPrice, Integer type,
                                                Integer serviceType);

    /**
     * 领取优惠券
     * @param userId
     * @param couponInfo
     * @return
     */
    ResultResponse receiveCoupon(Integer userId, CouponInfo couponInfo);

    /**
     * 获取优惠券数量
     * @param userId
     * @return
     */
    int countByUserIdAndIsDeleteAndAndCpnType(Integer userId, Integer shopId, List<Integer> categoryIds, BigDecimal totalPrice);

    /**
     * 卡包
     * @param userId
     * @return
     */
    ResultResponse couponList(Integer userId);


    //---------------------------------------------------------- 商户端 -----------------------------------------------


}
