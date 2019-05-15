package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description 订单收货地址JPA
 * @Author onlineS
 * @Date 2019/4/28
 * @Version 1.0
 **/
public interface OrderAddressRepository extends JpaRepository<OrderAddress, Integer> {
    List<OrderAddress> findByOrderId(Integer orderId);
}
