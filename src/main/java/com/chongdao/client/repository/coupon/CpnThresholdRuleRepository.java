package com.chongdao.client.repository.coupon;

import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CpnThresholdRuleRepository extends JpaRepository<CpnThresholdRule,Integer> {
    @Transactional
    @Modifying
    @Query(value = "update cpn_threshold_rule set is_delete=1 where cpn_id=?1",nativeQuery = true)
    void updateState(Integer cpnId);

    CpnThresholdRule findByCpnId(Integer cpnId);
}
