package com.chongdao.client.mapper;

import com.chongdao.client.entitys.PetBreed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetBreedMapper {
    List<PetBreed> getDataByTypeAndName(@Param("type") Integer type, @Param("name") String name, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
