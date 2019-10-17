package com.chongdao.client.repository;

import com.chongdao.client.entitys.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Good, Integer> {


    @Query(value = "update good set ratio = ?1 where goods_type_id = ?2 and shop_id = ?3", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateRatioAndGoodsTypeIdAndShopId(Double ratio, Integer goodsTypeId, Integer shopId);

    @Query(value = "update good set ratio = ?1 where   shop_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateRatioAndShopId(Double ratio, Integer shopId);

    /**
     * 一键恢复原价
     *
     * @param shopId
     * @return
     */
    @Query(value = "update good set ratio = 1 where  shop_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    Integer updateRatio(Integer shopId);

    /**
     * 获取店铺销量
     * @param shopId
     * @return
     */
    @Query(value = "select sum(sales) from good where shop_id=?1",nativeQuery = true)
    Integer findBySalesSum(Integer shopId);

    /**
     * 查询店铺所有商品
     * @param shopId
     * @return
     */
    List<Good> findAllByShopIdIn(List<Integer> shopId);


    /**
     * 查询商品
     * @param goodsId
     * @return
     */
    List<Good> findAllByIdAndStatus(List<Integer> goodsId,Integer status);

    Good findByIdAndStatus(Integer goodsId, Byte status);


    List<Good> findByShopIdAndGoodsTypeIdAndStatus(Integer shopId, Integer goodsTypeId,Byte status);


    Optional<List<Good>> findAllByIdIn(List<Integer> goodsId);


    @Transactional
    @Query(value = "update good set sales = sales + ?1 where id = ?2",nativeQuery = true)
    @Modifying
    int updateGoodIdIn(Integer count,Integer goodsId);

    List<Good> findByShopIdAndGoodsTypeId(Integer shopId, Integer goodsTypeId);

    List<Good> findByShopIdAndStatus(Integer shopId, Byte status);

    List<Good> findByShopIdAndStatus(Integer shopId, Integer status);



}
