package com.chongdao.client.repository;

import com.chongdao.client.entitys.ShopBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ShopBillRepository extends JpaRepository<ShopBill, Integer> {
    List<ShopBill> findByOrderIdAndPriceGreaterThan(Integer orderId, BigDecimal price);
}
