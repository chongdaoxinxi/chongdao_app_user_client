package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderInfo;

import java.math.BigDecimal;

public interface ShopBillService {
    ResultResponse addShopBillRecord(OrderInfo orderInfo, Integer type, String note, BigDecimal realMoney);
}
