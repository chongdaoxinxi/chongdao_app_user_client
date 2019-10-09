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

    ResultResponse getInsuranceDataList(String token, Integer insuranceType, String userName, String phone, String insuranceOrderNo, Date start, Date end, Integer status, Integer pageNum, Integer pageSize);

    ResultResponse auditInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note);

    ResultResponse refuseInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note);

    ResultResponse pollingCheckOrderStatus(Integer insuranceOrderId);

    ResultResponse getInsuranceUserTodo(String token);
}
