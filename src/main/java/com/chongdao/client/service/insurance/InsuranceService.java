package com.chongdao.client.service.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;

public interface InsuranceService {
    ResultResponse saveMedicalIusurance(InsuranceOrder medicalInsuranceOrder);
}
