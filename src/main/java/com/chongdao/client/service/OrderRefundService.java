package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderInfo;

public interface OrderRefundService {
    ResultResponse addOrderRefundRecord(OrderInfo orderInfo, Integer type, String note);

    ResultResponse getRefundData(Integer orderId);
}
