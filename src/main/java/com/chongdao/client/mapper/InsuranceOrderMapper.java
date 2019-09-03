package com.chongdao.client.mapper;

import com.chongdao.client.entitys.InsuranceOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface InsuranceOrderMapper {
    List<InsuranceOrder> getInsuranceDataList(@Param("insuranceType") Integer insuranceType, @Param("userName") String userName, @Param("phone") String phone, @Param("insuranceOrderNo") String insuranceOrderNo, @Param("start") Date start, @Param("end") Date end, @Param("status") Integer status);
}
