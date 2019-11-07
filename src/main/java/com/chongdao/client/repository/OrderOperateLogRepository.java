package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderOperateLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderOperateLogRepository extends JpaRepository<OrderOperateLog, Integer> {
}
