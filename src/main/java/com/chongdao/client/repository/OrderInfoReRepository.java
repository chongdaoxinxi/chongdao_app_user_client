package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderInfoRe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoReRepository extends JpaRepository<OrderInfoRe, Integer> {

    OrderInfoRe findByReOrderNoAndStatusAndUserId(String reOrderNo, Integer status,Integer userId);

    OrderInfoRe findByReOrderNo(String reOrderNo);
}
