package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface ExpressRuleService {
    ResultResponse getExpressRule(String token);
    ResultResponse saveExpressRule(String token, String startTime, String endTime);
}
