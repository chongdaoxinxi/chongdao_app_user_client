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

    Carts selectCartByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId,@Param("shopId")Integer shopId,@Param("petId")Integer petId);

    List<Carts> selectCartByUserId(@Param("userId") Integer userId,@Param("shopId") Integer shopId);

    int selectCartProductCheckedStatusByUserId(Integer userId);
    void updateCartByUserIdAndGoodsId(@Param("userId") Integer userId, @Param("goodsId") Integer goodsId,@Param("shopId")Integer shopId,@Param("petId")Integer petId);

    /**
     * 删除购物车的商品
     * @param userId
     * @param goodsId
     */
    void deleteByUserIdAndProductIds(@Param("userId") Integer userId, @Param("shopId") Integer shopId,@Param("goodsId") Integer goodsId,@Param("petId")Integer petId);

    void clearCart(@Param("userId") Integer userId, @Param("shopId") Integer shopId);

    List<Carts> selectCheckedCartByUserId(@Param("userId") Integer userId,@Param("shopId") Integer shopId, @Param("goodsId") Integer goodsId);
}