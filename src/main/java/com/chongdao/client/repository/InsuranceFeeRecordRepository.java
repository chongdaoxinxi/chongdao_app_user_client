package com.chongdao.client.repository;

import com.chongdao.client.entitys.InsuranceFeeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceFeeRecordRepository extends JpaRepository<InsuranceFeeRecord, Integer> {
    List<InsuranceFeeRecord> findByOrderNo(String orderNo);

    List<InsuranceFeeRecord> findByStatus(Integer status);
}
