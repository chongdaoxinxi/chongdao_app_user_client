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
     * @param categoryId
     * @param status
     * @return
     */
    List<GoodsType> findByCategoryIdNotAndStatus(Integer categoryId,Integer status);

    List<GoodsType> findByStatusAndCategoryId(Integer status,Integer categoryId);

    List<GoodsType> findByStatusAndCategoryIdNotAndIdNot(Integer status,Integer categoryId,Integer goodsTypeId);


    List<GoodsType> findByParentIdAndStatus(Integer parentId, Integer status);

    List<GoodsType> findByStatusAndParentIdNot(Integer status, Integer parentId);

    List<GoodsType> findByCategoryIdAndStatusAndParentId(Integer categoryId, Integer status, Integer parentId);

    List<GoodsType> findByParentIdAndStatusAndCategoryId(Integer parentId, Integer status,Integer categoryId);

    List<GoodsType> findByCategoryIdNotAndParentIdAndStatus(Integer categoryId, Integer parentId, Integer status);


}
