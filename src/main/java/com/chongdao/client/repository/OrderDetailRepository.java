package com.chongdao.client.repository;

import com.chongdao.client.entitys.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByUserIdAndOrderNo(Integer userId,String orderNo);

    List<OrderDetail> findByUserIdAndReOrderNo(Integer userId,String reOrderNo);
}
