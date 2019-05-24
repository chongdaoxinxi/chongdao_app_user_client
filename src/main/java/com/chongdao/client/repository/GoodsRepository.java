package com.chongdao.client.repository;

import com.chongdao.client.entitys.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface GoodsRepository extends JpaRepository<Good,Integer> {


    @Query(value = "update good set ratio = ?1 where good_type_id = ?2 and shop_id = ?3",nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateRatioAndGoodTypeIdAndShopId(Double ratio,Integer goodsTypeId,Integer shopId);

    @Query(value = "update good set ratio = ?1 where   shop_id = ?2",nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateRatioAndShopId(Double ratio,Integer shopId);

    /**
     * 一键恢复原价
     * @param shopId
     * @return
     */
    @Query(value = "update good set ratio = 1 where  shop_id = ?1",nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateRatio(Integer shopId);
}
