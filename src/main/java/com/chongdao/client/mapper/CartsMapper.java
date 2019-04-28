package com.chongdao.client.mapper;

import com.chongdao.client.entitys.Carts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Carts record);

    int insertSelective(Carts record);

    Carts selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Carts record);

    int updateByPrimaryKey(Carts record);

    Carts selectCartByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId);

    List<Carts> selectCartByUserId(Integer userId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    /**
     * 删除购物车的商品
     * @param userId
     * @param goodsList
     */
    void deleteByUserIdAndProductIds(Integer userId, List<String> goodsList);
}