package com.chongdao.client.service.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;

public interface InsuranceExternalService {
    ResultResponse generateInsure(InsuranceOrder insuranceOrder);

}
