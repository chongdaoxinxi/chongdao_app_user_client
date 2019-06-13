package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderRefund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRefundRepository extends JpaRepository<OrderRefund, Integer> {
    Optional<List<OrderRefund>> findByOrderId(Integer orderId);
}
