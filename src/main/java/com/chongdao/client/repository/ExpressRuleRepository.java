package com.chongdao.client.repository;

import com.chongdao.client.entitys.ExpressRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpressRuleRepository extends JpaRepository<ExpressRule, Integer> {
    Optional<ExpressRule> findByAreaCode(String areaCode);
}
