package com.chongdao.client.repository;

import com.chongdao.client.entitys.GoodsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsTypeRepository extends JpaRepository<GoodsType,Integer> {
    List<GoodsType> findAllByCategoryIdAndStatus(Integer categoryId, Integer status);

    List<GoodsType> findByCategoryIdInAndStatusAndIdNot(List<Integer> categoryIds, Integer status,Integer goodsTypeId);

    List<GoodsType> findByParentId(Integer parentId);

    /**
     * 获取非商品类别
     * @param categoryIds
     * @param status
     * @return
     */
    List<GoodsType> findByCategoryIdNotInAndStatus(List<Integer> categoryIds,Integer status);

    List<GoodsType> findByStatusAndCategoryId(Integer status,Integer categoryId);

    List<GoodsType> findByStatusAndCategoryIdNotAndIdNot(Integer status,Integer categoryId,Integer goodsTypeId);

    List<GoodsType> findByStatus(Integer status);

    List<GoodsType> findByParentIdAndStatus(Integer parentId, Integer status);

}
