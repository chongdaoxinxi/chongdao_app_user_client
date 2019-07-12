package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface StatisticalService {
    ResultResponse getUserVisitStatistical(String token, Integer dateType);
    ResultResponse getOrderStatistical(String token, Integer dateType);
    ResultResponse getUserWeekVisitStatistical(String token, Integer dateType);
    ResultResponse getStatisticalData(String token, Integer dateType);
}
