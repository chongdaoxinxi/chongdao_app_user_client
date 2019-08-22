package com.chongdao.client.repository;

import com.chongdao.client.entitys.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer> {
    Optional<List<Brand>> findByGoodsTypeId(Integer goodsTypeId);


    List<Brand> findByGoodsTypeIdIn(List<Integer> goodsTypeIdList);

}
