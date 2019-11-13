package com.chongdao.client.repository;

import com.chongdao.client.entitys.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer> {
    Optional<List<Brand>> findByGoodsTypeId(Integer goodsTypeId);


    List<Brand> findByGoodsTypeIdIn(List<Integer> goodsTypeIdList);

    @Query(value = "select A.name from (select count(name) as count, name from brand GROUP BY name ) A  where A.count > 1 ", nativeQuery = true)
    List<String> getRepeatedBrand();

    List<Brand> findByName(String name);
}
