package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.HTOrderInfoVO;

public interface LivingService {
    ResultResponse preOrCreateOrder(HTOrderInfoVO htOrderInfoVO);
}

