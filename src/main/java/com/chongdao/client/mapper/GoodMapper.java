package com.chongdao.client.mapper;

import com.chongdao.client.entitys.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
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
}