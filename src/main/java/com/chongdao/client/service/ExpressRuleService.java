package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.ExpressRule;

public interface ExpressRuleService {
    ResultResponse getExpressRule(String token);
    ResultResponse saveExpressRule(String token, ExpressRule expressRule);
}
