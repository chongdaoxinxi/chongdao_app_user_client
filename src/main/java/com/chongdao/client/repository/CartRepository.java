package com.chongdao.client.repository;

import com.chongdao.client.entitys.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface CartRepository extends JpaRepository<Carts,Integer> {
    int countByUserIdAndShopIdAndChecked(Integer userId,Integer shopId,Byte checked);

    @Transactional
    void deleteByUserIdAndShopId(Integer userId, Integer shopId);
}
