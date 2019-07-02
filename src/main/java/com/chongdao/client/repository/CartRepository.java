package com.chongdao.client.repository;

import com.chongdao.client.entitys.Carts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Carts,Integer> {
    int countByUserIdAndShopIdAndChecked(Integer userId,Integer shopId,Byte checked);
}
