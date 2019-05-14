package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Coupon;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shop_coupon_manage/")
public class ShopCouponManageController {

    /**
     * 获取满减券列表
     * @param token
     * @return
     */
    public ResultResponse getShopFullReductionCouponList(String token) {
        return null;
    }

    /**
     * 获取优惠券列表
     * @param token
     * @return
     */
    public ResultResponse getShopCustomCouponList(String token) {
        return null;
    }

    /**
     * 添加满减券/优惠券
     * @param coupon
     * @return
     */
    public ResultResponse addCounpon(Coupon coupon) {
        return null;
    }

    /**
     * 下架满减券/优惠券
     * @param couponId
     * @return
     */
    public ResultResponse offShelveCoupon(Integer couponId) {
        return null;
    }

    /**
     * 删除满减券/优惠券
     * @param couponId
     * @return
     */
    public ResultResponse removeCoupon(Integer couponId) {
        return null;
    }
}
