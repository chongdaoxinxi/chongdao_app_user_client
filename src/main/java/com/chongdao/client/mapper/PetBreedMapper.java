package com.chongdao.client.mapper;

import com.chongdao.client.entitys.PetBreed;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetBreedMapper {
    List<PetBreed> getDataByTypeAndName(Integer type, String name);
}
