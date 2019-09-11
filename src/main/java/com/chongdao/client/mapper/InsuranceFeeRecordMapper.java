package com.chongdao.client.mapper;

import com.chongdao.client.entitys.InsuranceFeeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface InsuranceFeeRecordMapper {
    List<InsuranceFeeRecord> getInsuranceFeeRecordData(@Param("shopId") Integer shopId, @Param("userName")String userName, @Param("shopName") String shopName, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
 }
