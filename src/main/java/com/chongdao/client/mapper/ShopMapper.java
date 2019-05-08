package com.chongdao.client.mapper;

import com.chongdao.client.entitys.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    List<Shop> selectByName(@Param("shopName") String shopName, @Param("orderBy") String orderBy,
                            @Param("categoryId") String categoryId,
                            @Param("discount") Integer discount,@Param("proActivities") String proActivities);
}