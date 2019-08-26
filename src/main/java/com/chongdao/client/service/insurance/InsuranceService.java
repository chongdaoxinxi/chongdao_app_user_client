package com.chongdao.client.service.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;

import java.util.Date;

public interface InsuranceService {
    ResultResponse saveInsurance(InsuranceOrder insuranceOrder);

    ResultResponse getMyInsuranceData(String token, Integer pageSize, Integer pageNum);

    ResultResponse getInsuranceDetail(Integer insuranceId);

    ResultResponse getInsuranceDataList(String token, String userName, String insuranceOrderNo, Date start, Date end, Integer pageNum, Integer pageSize);

    ResultResponse auditInsurance(Integer insuranceOrderId, Integer targetStatus, String note);

    ResultResponse refuseInsurance(Integer insuranceOrderId, Integer targetStatus, String note);
}
