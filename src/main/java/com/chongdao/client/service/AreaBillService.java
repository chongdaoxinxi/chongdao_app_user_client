package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.util.Date;

public interface AreaBillService {
    ResultResponse getAreaBillByManagementId(Integer managementId, String shopName, Date startDate, Date endDate, Integer type, Integer pageNum, Integer pageSize);

    ResultResponse getAreaAccountMoneyData(Integer managementId);
}
