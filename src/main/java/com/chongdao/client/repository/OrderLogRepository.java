package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLogRepository extends JpaRepository<OrderLog, Integer> {
}
