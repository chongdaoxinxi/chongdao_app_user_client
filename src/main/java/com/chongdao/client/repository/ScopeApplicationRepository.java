package com.chongdao.client.repository;

import com.chongdao.client.entitys.ScopeApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScopeApplicationRepository extends JpaRepository<ScopeApplication,Integer> {
    List<ScopeApplication> findAllById(Integer petCategoryId);

    //List<ScopeApplication> findByBrandIdAndType(Integer brandId,Integer type);

    List<ScopeApplication> findByGoodsTypeId(Integer goodsTypeId);
}
