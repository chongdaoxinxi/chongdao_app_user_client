package com.chongdao.client.repository;

import com.chongdao.client.entitys.AreaBill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface AreaBillRepository extends JpaRepository<AreaBill, Integer> {
    /**
     * 类型为订单消费的入账记录
     * @param orderId
     * @param type
     * @param price
     * @return
     */
    List<AreaBill> findByOrderIdAndTypeOrTypeAndPriceGreaterThan(Integer orderId, Integer type, Integer type2, BigDecimal price);
}
