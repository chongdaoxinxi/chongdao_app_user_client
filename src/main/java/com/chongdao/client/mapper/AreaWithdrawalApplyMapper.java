package com.chongdao.client.mapper;

import com.chongdao.client.entitys.AreaWithdrawalApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AreaWithdrawalApplyMapper {
    List<AreaWithdrawalApply> getAreaWithdrawApplyList(@Param("managementId") Integer managementId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
