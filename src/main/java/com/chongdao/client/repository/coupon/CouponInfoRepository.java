package com.chongdao.client.repository.coupon;

import com.chongdao.client.entitys.coupon.CouponInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponInfoRepository extends JpaRepository<CouponInfo,Integer> {
    Iterable<CouponInfo> findAllByCpnState(Integer state);

    @Transactional
    @Modifying
    @Query(value = "update cpn_info set cpn_state = ?2 where id = ?1",nativeQuery = true)
    void updateState(Integer cpnId, Integer state);


    /**
     * 首页商家展示优惠活动
     * @param shopId
     * @param cpnState
     * @return
     */
    List<CouponInfo> findByShopIdInAndCpnState(Integer shopId,Integer cpnState);

    List<CouponInfo> findByShopIdInAndCpnStateAndCpnTypeOrderByCpnValueDesc(Integer shopId,Integer cpnState, Integer cpnType);

    List<CouponInfo> findByShopIdAndCpnStateAndCpnTypeNotOrderByCpnValueDesc(Integer shopId, Integer cpnState, Integer cpnType);

    List<CouponInfo> findByIdAndShopIdAndCpnStateAndCpnTypeNot(Integer cpnId,Integer shopId, Integer cpnState, Integer cpnType);

    List<CouponInfo> findByShopIdAndCpnStateAndCpnTypeIn(Integer shopId, Integer cpnState, List<Integer> cpnType);

    /**
     * 更新已领取的优惠券数量
     * @param couponId
     */
    @javax.transaction.Transactional
    @Query(value = "update cpn_info set gain_number = gain_number +1 where id = ?1 and shop_id=?2" ,nativeQuery = true)
    @Modifying
    void updateReceiveCountByCouponId(@Param("couponId") Integer couponId, @Param("shopId") String shopId);

    List<CouponInfo> findAllByShopIdAndCpnStateAndCpnType(Integer shopId,Integer state,Integer cpnType);

    List<CouponInfo> findAllByShopIdAndCpnStateAndCpnTypeNot(Integer shopId,Integer state,Integer cpnType);

    List<CouponInfo> findAllByIdIn(List<Integer> ids);


    CouponInfo findByShopIdAndIdAndCpnStateAndCpnTypeNot(Integer shopId,Integer cpnId, Integer cpnState, Integer cpnType);





    //-------------------------用户------------------------------------

}
