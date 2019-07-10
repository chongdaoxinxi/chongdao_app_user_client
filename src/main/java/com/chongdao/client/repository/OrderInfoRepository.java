package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer> {
    Optional<List<OrderInfo>> findByUserIdAndShopId(Integer userId, Integer shopId);

    OrderInfo findByOrderNo(String orderNo);
}
