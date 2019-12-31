package com.chongdao.client.repository;

import com.chongdao.client.entitys.InsuranceOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceOrderRepository extends JpaRepository<InsuranceOrder, Integer> {
    List<InsuranceOrder> findByUserId(Integer userId, Pageable pageable);

    List<InsuranceOrder> findByInsuranceOrderNo(String insuranceOrderNo);

    List<InsuranceOrder> findByProposalNo(String proposalNo);

    List<InsuranceOrder> findByUserIdAndStatusGreaterThan(Integer userId, Integer status);

    List<InsuranceOrder> findByStatus(Integer status);

    List<InsuranceOrder> findByOrderNo(String orderNo);
}
