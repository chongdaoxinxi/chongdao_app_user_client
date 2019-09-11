package com.chongdao.client.mapper;

import com.chongdao.client.entitys.InsuranceShopChip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface InsuranceShopChipMapper {
    List<InsuranceShopChip> getShopChipDataList(@Param("shopId") Integer shopId, @Param("core") String core, @Param("status") Integer status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
