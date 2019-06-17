package com.chongdao.client.mapper;

import com.chongdao.client.entitys.Carts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Carts record);

    int insertSelective(Carts record);

    Carts selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Carts record);

    int updateByPrimaryKey(Carts record);

    Carts selectCartByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId,@Param("shopId")Integer shopId);

    List<Carts> selectCartByUserId(@Param("userId") Integer userId,@Param("shopId") Integer shopId);

    int selectCartProductCheckedStatusByUserId(Integer userId);

    /**
     * 删除购物车的商品
     * @param userId
     * @param goodsList
     */
    void deleteByUserIdAndProductIds(Integer userId, List<String> goodsList);

    List<Carts> selectCheckedCartByUserId(@Param("userId") Integer userId,@Param("shopId") Integer shopId);
}