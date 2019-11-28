package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.math.BigDecimal;
import java.util.Date;

public interface ShopBillService {
    ResultResponse addShopBillRecord(Integer orderId, Integer shopId, Integer type, String note, BigDecimal realMoney);

    ResultResponse getShopBillByShopId(Integer shopId, Date startDate, Date endDate, Integer pageNum, Integer pageSize);

    ResultResponse getShopBillByAreaCode(Integer managementId, String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageSize);

    ResultResponse getShopBillOrderDetailById(Integer shopBillId);
}
