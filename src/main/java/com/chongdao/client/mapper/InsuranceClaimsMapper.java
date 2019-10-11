package com.chongdao.client.mapper;

import com.chongdao.client.entitys.InsuranceClaims;
import com.chongdao.client.vo.UserInsuranceTodoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface InsuranceClaimsMapper {
    List<InsuranceClaims> getMyClaimsList(@Param("userId") Integer userId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    InsuranceClaims getClaimsByInsuranceOrderId(@Param("insuranceOrderId") Integer insuranceOrderId);

    List<UserInsuranceTodoVO> getUserTodoList(@Param("userId") Integer userId);
}
