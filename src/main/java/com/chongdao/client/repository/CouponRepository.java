package com.chongdao.client.repository;

import com.chongdao.client.entitys.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    /**
     * 查询优惠券
     * @param shopId
     * @param status
     * @param type
     * @return
     */
    List<Coupon> findByShopIdAndStatusAndType(Integer shopId, Integer status, Integer type);

    /**
     * 查询商家优惠券数量
     * @param userId
     * @param shopId
     * @return
     */
    @Query(value = "select count(1) from card_user c1 inner join coupon c2 on c1.coupon_id = c2.id where c2.status=1 and c2.shop_id = c1.shop_id" +
            " and end_time > NOW() and c1.count >0 and c1.user_id=?1 and c1.shop_id =?2 and c2.type=2", nativeQuery = true)
    Integer findByCouponCount(Integer userId, Integer shopId);


    @Query(value = "select c1.card_id,c2.shop_id,c2.category_id,c2.full_price,c2.decrease_price , c2.start_time,c2.end_time  " +
            "from card_user c1 inner join coupon c2 on c1.coupon_id = c2.id where c2.status=1 and c2.shop_id = c1.shop_id" +
            " and end_time > NOW() and c1.count >0 and c1.user_id=?1 and c1.shop_id =?2 and c2.type=2", nativeQuery = true)
    List<Coupon> findByUserIdCoupons(Integer userId, Integer shopId);



    /**
     * 更新已领取的优惠券数量
     * @param couponId
     */
    @javax.transaction.Transactional
    @Query(value = "update coupon set receive_coupon_count = receive_coupon_count +1 where id = ?1 and shop_id=?2" ,nativeQuery = true)
    @Modifying
    void updateReceiveCountByCouponId(@Param("couponId") Integer couponId, @Param("shopId") Integer shopId);



    //----------------------------------------------商户端---------------------------------------------



    /**
     * 更新优惠券状态
     * @param couponId
     * @param status
     */
    @Modifying
    @Query("update Coupon c set c.status = ?2 where id = ?1")
    @Transactional
    void updateCouponStatusById(@Param("couponId") Integer couponId, @Param("status") Integer status);


    /**
     * 根据shopId查找满减优惠券
     * @param shopId
     * @return
     */
    List<Coupon> findByShopIdAndTypeInAndStatusNotOrderByCreateTimeDesc(Integer shopId, List<Integer> typeList,Integer status);
}
