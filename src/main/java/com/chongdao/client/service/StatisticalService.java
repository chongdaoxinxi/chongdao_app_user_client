package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface StatisticalService {
    ResultResponse getUserVisitStatistical(String token, Integer type);
    ResultResponse getOrderStatistical(String token, Integer type);
    ResultResponse getUserWeekVisitStatistical(String token, Integer type);
}
