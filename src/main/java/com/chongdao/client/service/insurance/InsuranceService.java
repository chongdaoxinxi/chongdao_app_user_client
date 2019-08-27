package com.chongdao.client.service.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;

import java.io.IOException;
import java.util.Date;

public interface InsuranceService {
    ResultResponse saveInsurance(InsuranceOrder insuranceOrder) throws IOException;

    ResultResponse getMyInsuranceData(String token, Integer pageSize, Integer pageNum);

    ResultResponse getInsuranceDetail(Integer insuranceId);

    ResultResponse downloadElectronicInsurancePolicy(Integer insuranceId);

    ResultResponse getInsuranceDataList(String token, String userName, String insuranceOrderNo, Date start, Date end, Integer pageNum, Integer pageSize);

    ResultResponse auditInsurance(Integer insuranceOrderId, Integer targetStatus, String note);

    ResultResponse refuseInsurance(Integer insuranceOrderId, Integer targetStatus, String note);
}
