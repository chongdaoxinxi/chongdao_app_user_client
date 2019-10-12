package com.chongdao.client.mapper;

import com.chongdao.client.entitys.InsuranceFeeRecord;
import com.chongdao.client.vo.UserInsuranceTodoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface InsuranceFeeRecordMapper {
    List<InsuranceFeeRecord> getInsuranceFeeRecordData(@Param("shopId") Integer shopId, @Param("userName")String userName, @Param("shopName") String shopName, @Param("status") Integer status, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<InsuranceFeeRecord> getUserInsuranceFeeRecordList(@Param("userId") Integer userId, @Param("status") Integer status, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<UserInsuranceTodoVO>  getUserTodoList(@Param("userId") Integer userId);

    InsuranceFeeRecord getInsuranceFeeRecordById(@Param("id") Integer id);
}
