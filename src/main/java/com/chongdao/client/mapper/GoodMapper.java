package com.chongdao.client.mapper;

import com.chongdao.client.entitys.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component(value = "shopMapper")
public interface GoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    int insertSelective(Good record);

    Good selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);


    /**
     * 查询所有上架状态的商品
     * @return
     */
    List<Good> selectList();

    List<Good> selectByName(@Param("goodsName") String goodsName, @Param("orderBy") String orderBy, @Param("categoryId") String categoryId,
                            @Param("discount") Integer discount,@Param("proActivities") String proActivities);

    List<Good> getFavouriteGoodList(@Param("userId") Integer userId);

    List<Good> selectListByShopId(@Param("shopId")Integer shopId);

    List<Good> selectByShopIdAndCategoryId(@Param("shopId") Integer shopId, @Param("categoryId")Integer categoryId);
}