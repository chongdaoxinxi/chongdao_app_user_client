package com.chongdao.client.mapper;

import com.chongdao.client.entitys.Shop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    List<Shop> getMyFavouriteShopList(Integer userId);

    List<Shop> selectByName(@Param("orderBy") String orderBy,
                            @Param("lng") Double lng,@Param("lat")Double lat,
                            @Param("categoryId") String categoryId,
                            @Param("discount") Integer discount,@Param("proActivities") String proActivities);

    List<Shop> selectByAreaCodeAndShopName(@Param("areaCode") String areaCode, @Param("shopName") String shopName);

    List<Shop> listGeo(@Param("lng") Double lng,@Param("lat")Double lat);
}