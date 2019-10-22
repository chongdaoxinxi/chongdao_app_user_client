package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.math.BigDecimal;
import java.util.Date;

public interface AreaWithdrawalApplyService {
    /**
     * 申请提现
     * @param token
     * @param applyMoney
     * @param applyNote
     * @return
     */
    ResultResponse applyAreaWithdrawal(String token, BigDecimal applyMoney, String applyNote);

    ResultResponse getAreaWithdrawApplyListData(Integer managementId, String name, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 审核提现
     * @param userWithdrawalId
     * @param note
     * @param realMoney
     * @param targetStatus
     * @return
     */
    ResultResponse checkAreaWithdrawal(Integer userWithdrawalId, String note, BigDecimal realMoney, Integer targetStatus);
}
