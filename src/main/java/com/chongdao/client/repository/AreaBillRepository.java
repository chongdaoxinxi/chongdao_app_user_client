package com.chongdao.client.repository;

import com.chongdao.client.entitys.AreaBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaBillRepository extends JpaRepository<AreaBill, Integer> {
    List<AreaBill> findByOrderIdAndType(Integer orderId, Integer price);
}
