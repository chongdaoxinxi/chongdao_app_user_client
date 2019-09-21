package com.chongdao.client.repository;

import com.chongdao.client.entitys.HtOrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface HtOrderInfoRepository extends JpaRepository<HtOrderInfo,Integer> {

    HtOrderInfo findByHtOrderNoAndOrderStatusAndBuyerUserId(String htOrderNo, Integer status, Integer userId);

    HtOrderInfo findByHtOrderNo(String htOrderNo);

    @Transactional
    @Query(value = "update ht_order_info set order_status = 1 where ht_order_no=?1",nativeQuery = true)
    @Modifying
    void updateHtOrderInfoOrderStatus(String htOrderNo);
}
