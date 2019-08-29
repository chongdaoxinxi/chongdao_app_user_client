package com.chongdao.client.mapper;

import com.chongdao.client.entitys.InsuranceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface InsuranceOrderMapper {
    List<InsuranceOrder> getInsuranceDataList(@Param("userName") String userName, @Param("insuranceOrderNo") String insuranceOrderNo, @Param("start") Date start, @Param("end") Date end);
}
