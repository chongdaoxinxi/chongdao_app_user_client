package com.chongdao.client.repository;

import com.chongdao.client.entitys.SuperAdminBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface SuperAdminBillRepository extends JpaRepository<SuperAdminBill, Integer> {
    /**
     * 类型为订单消费的入账记录
     * @param orderId
     * @param type
     * @param price
     * @return
     */
    List<SuperAdminBill> findByOrderIdAndTypeAndPriceGreaterThan(Integer orderId, Integer type, BigDecimal price);
}
