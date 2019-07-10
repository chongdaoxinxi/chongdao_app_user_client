package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderExpressEval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderExpressEvalRepository extends JpaRepository<OrderExpressEval,Integer> {
    OrderExpressEval findByOrderNo(String orderNo);
}
