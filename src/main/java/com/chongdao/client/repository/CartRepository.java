package com.chongdao.client.repository;

import com.chongdao.client.entitys.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CartRepository extends JpaRepository<Carts,Integer> {
    @Query(value = "select ifnull(sum(quantity),0) from carts where user_id = ?1 and shop_id = ?2 and checked=1",nativeQuery = true)
    int countByUserIdAndShopId(@Param("userId") Integer userId, @Param("shopId")  Integer shopId);

    @Transactional
    void deleteByUserIdAndShopId(Integer userId, Integer shopId);
}
