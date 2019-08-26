package com.chongdao.client.service.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;

import java.io.IOException;

public interface InsuranceExternalService {
    ResultResponse generateInsure(InsuranceOrder insuranceOrder) throws IOException;

}
