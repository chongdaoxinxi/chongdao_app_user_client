package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderPetInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPetInfoRepository extends JpaRepository<OrderPetInfo, Integer> {
    List<OrderPetInfo> findByOrderNo(String orderNo);
}
