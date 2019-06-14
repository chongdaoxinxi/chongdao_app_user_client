package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.math.BigDecimal;

public interface ShopBillService {
    ResultResponse addShopBillRecord(Integer orderId, Integer shopId, Integer type, String note, BigDecimal realMoney);
}
