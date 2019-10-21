package com.chongdao.client.repository;

import com.chongdao.client.entitys.ShopBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ShopBillRepository extends JpaRepository<ShopBill, Integer> {
    /**
     * 类型为订单消费的入账记录
     * @param orderId
     * @param type
     * @param price
     * @return
     */
    List<ShopBill> findByOrderIdAndTypeAndPriceGreaterThan(Integer orderId, Integer type, BigDecimal price);
}
