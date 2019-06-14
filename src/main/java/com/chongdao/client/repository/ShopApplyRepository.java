package com.chongdao.client.repository;

import com.chongdao.client.entitys.ShopApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopApplyRepository extends JpaRepository<ShopApply, Integer> {
    @Query(value="SELECT sa.*, s.shop_name as shopName FROM shop_apply sa" +
            " left join shop s on sa.shop_id = s.id" +
            " where s.shop_name like concat('%', ?1, '%') or ?1 is null" +
            " order by sa.create_time limit ?2, ?3", nativeQuery = true)
    List<ShopApply> getShopAppliesByShopName(String shopName, Integer pageNum, Integer pageSize);
}
