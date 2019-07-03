package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.util.Date;

public interface AreaWithdrawalApplyService {
    ResultResponse getAreaWithdrawApplyListData(Integer managementId, Date startDate, Date endDate, Integer pageNum, Integer pageSize);
}
