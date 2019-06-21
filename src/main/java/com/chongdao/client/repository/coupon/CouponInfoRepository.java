package com.chongdao.client.repository.coupon;

import com.chongdao.client.entitys.coupon.CouponInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponInfoRepository extends JpaRepository<CouponInfo,Integer> {
    Iterable<CouponInfo> findAllByCpnState(Integer state);

}
