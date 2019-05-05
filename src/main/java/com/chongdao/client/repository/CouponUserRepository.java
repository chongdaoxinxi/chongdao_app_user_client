package com.chongdao.client.repository;

import com.chongdao.client.entitys.CouponUser;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CouponUserRepository extends JpaRepository<CouponUser,Integer> {

    CouponUser findByCouponIdAndShopIdAndUserId(Integer couponId, Integer shopId, Integer userId);


    //根据用户查询优惠券
    CouponUser findByUserIdAndCouponIdAndShopId(Integer userId, Integer couponId,Integer shopId);







}
