package com.chongdao.client.mapper;

import com.chongdao.client.entitys.InsuranceFeeRecord;
import com.chongdao.client.vo.UserInsuranceTodoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface InsuranceFeeRecordMapper {
    List<InsuranceFeeRecord> getInsuranceFeeRecordData(@Param("shopId") Integer shopId, @Param("userName")String userName, @Param("shopName") String shopName, @Param("status") Integer status, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<InsuranceFeeRecord> getUserInsuranceFeeRecordList(@Param("userId") Integer userId, @Param("status") Integer status, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    List<UserInsuranceTodoVO>  getUserTodoList(@Param("userId") Integer userId);

    InsuranceFeeRecord getInsuranceFeeRecordById(@Param("id") Integer id);

    /**
     * 获取申请理赔用户近15天内的有效医疗费用记录
     * @param userId
     * @return
     */
    BigDecimal getClaimsTotalFeeLimit15Days(@Param("userId") Integer userId);
}
