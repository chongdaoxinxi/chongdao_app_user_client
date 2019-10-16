package com.chongdao.client.repository.coupon;

import com.chongdao.client.entitys.coupon.CouponScopeRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CouponScopeRuleRepository extends JpaRepository<CouponScopeRule,Integer> {
    @Transactional
    @Modifying
    @Query(value = "update cpn_scope_rule set is_delete=1 where cpn_id=?1",nativeQuery = true)
    void updateState(Integer cpnId);


    List<CouponScopeRule> findByCategoryId(Integer categoryId);
}
