package com.chongdao.client.mapper;

import com.chongdao.client.entitys.AreaWithdrawalApply;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface AreaWithdrawalApplyMapper {
    List<AreaWithdrawalApply> getAreaWithdrawApplyList(Integer managementId, Date startDate, Date endDate);
}
