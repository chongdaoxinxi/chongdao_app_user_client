package com.chongdao.client.repository;

import com.chongdao.client.entitys.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    List<Coupon> findByShopIdAndStatusAndType(Integer shopId, Integer status, Integer type);
}
