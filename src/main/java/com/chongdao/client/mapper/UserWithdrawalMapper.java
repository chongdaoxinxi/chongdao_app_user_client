package com.chongdao.client.mapper;

import com.chongdao.client.entitys.UserWithdrawal;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserWithdrawalMapper {
    List<UserWithdrawal> getUserWithdrawalList(@Param("userId") Integer userId, @Param("name") String name, @Param("phone") String phone, @Param("status") Integer status, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
