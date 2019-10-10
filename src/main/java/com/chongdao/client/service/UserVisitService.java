package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface UserVisitService {
    ResultResponse addUserShopVisit(Integer userId, Integer shopId, Integer source);
    ResultResponse addUserSystemVisit(Integer userId, Integer source);
}
