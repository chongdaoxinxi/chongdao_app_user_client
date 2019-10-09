package com.chongdao.client.repository;

import com.chongdao.client.entitys.InsuranceShopChip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceShopChipRepository extends JpaRepository<InsuranceShopChip, Integer> {
    List<InsuranceShopChip> findByCore(String core);
}
