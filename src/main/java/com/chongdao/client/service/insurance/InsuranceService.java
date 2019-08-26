package com.chongdao.client.service.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;

public interface InsuranceService {
    ResultResponse saveInsurance(InsuranceOrder insuranceOrder);

    ResultResponse getMyInsuranceData(String token);

    ResultResponse getInsuranceDetail(Integer insuranceId);
}
