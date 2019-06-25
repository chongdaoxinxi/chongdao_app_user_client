package com.chongdao.client.mapper;

import com.chongdao.client.entitys.GoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsType record);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsType record);

    int updateByPrimaryKey(GoodsType record);

    List<GoodsType> selectByCategoryId(@Param("shopId") Integer shopId, @Param("categoryId") List<Integer> categoryId);



    //----------------------------------商户端------------------------------------------//
    
    List<GoodsType> getGoodCategoryList(Integer shopId);

    void updateGoodTypeStatus(@Param("goodTypeId") Integer goodId, @Param("status") Integer status);

}