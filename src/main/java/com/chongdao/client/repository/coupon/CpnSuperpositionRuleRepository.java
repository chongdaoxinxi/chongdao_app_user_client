package com.chongdao.client.repository.coupon;

import com.chongdao.client.entitys.coupon.CpnSuperpositionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CpnSuperpositionRuleRepository extends JpaRepository<CpnSuperpositionRule,Integer> {
    @Transactional
    @Modifying
    @Query(value = "update cpn_superposition_rule set is_delete=1 where cpn_id=?1",nativeQuery = true)
    void updateState(Integer cpnId);
}
