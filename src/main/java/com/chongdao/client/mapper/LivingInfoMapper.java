package com.chongdao.client.mapper;

import com.chongdao.client.entitys.LivingInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LivingInfoMapper {

    List<LivingInfo> findByTypeAndStatus(@Param("type") Integer type, @Param("orderBy") String orderBy,  @Param("status") Integer status);
}
