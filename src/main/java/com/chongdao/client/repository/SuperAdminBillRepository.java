package com.chongdao.client.repository;

import com.chongdao.client.entitys.SuperAdminBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperAdminBillRepository extends JpaRepository<SuperAdminBill, Integer> {
    List<SuperAdminBill> findByOrderIdAndType(Integer orderId, Integer price);
}
