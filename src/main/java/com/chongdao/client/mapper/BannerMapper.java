package com.chongdao.client.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BannerMapper {
    List<Mapper> getBannerListByAreaCodeAndStatus(@Param("areaCode") String areaCode, @Param("status") Integer status);
}
