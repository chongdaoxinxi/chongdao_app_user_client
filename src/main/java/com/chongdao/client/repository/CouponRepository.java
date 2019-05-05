package com.chongdao.client.repository;

import com.chongdao.client.entitys.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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



    /**
     * 更新已领取的优惠券数量
     * @param couponId
     */
    @javax.transaction.Transactional
    @Query(value = "update coupon set receive_coupon_count = receive_coupon_count +1 where id = ?1 and shop_id=?2" ,nativeQuery = true)
    @Modifying
    void updateReceiveCountByCouponId(@Param("couponId") Integer couponId, @Param("shopId") Integer shopId);

}
