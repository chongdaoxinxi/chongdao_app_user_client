package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserWithdrawal;

import java.math.BigDecimal;
import java.util.Date;

public interface UserWithdrawalService {
    /**
     * 申请提现
     * @param userWithdrawal
     * @return
     */
    ResultResponse addUserWithdrawal(UserWithdrawal userWithdrawal);

    /**
     * 获取用户提现列表
     * @param token
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse getUserWithdrawalList(String token, String name, String phone, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 审核用户提现
     * @param userWithdrawalId
     * @param note
     * @param realMoney
     * @param targetStatus
     * @return
     */
    ResultResponse checkUserWithdrawal(Integer userWithdrawalId, String note, BigDecimal realMoney, Integer targetStatus);
}
