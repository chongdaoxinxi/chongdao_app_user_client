package com.chongdao.client.repository;

import com.chongdao.client.entitys.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetCategoryRepository extends JpaRepository<PetCategory,Integer> {
    List<PetCategory> findByGoodsTypeId(Integer goodsTypeId);
}
