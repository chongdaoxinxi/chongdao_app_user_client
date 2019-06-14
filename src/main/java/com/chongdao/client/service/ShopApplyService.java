package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.math.BigDecimal;

public interface ShopApplyService {
    ResultResponse addShopApplyRecord(Integer shopId, BigDecimal applyMoney, String applyNote);

    ResultResponse acceptShopApplyRecord(Integer shopApplyId, BigDecimal realMoney, String checkNote);

    ResultResponse refuseShopApplyRecord(Integer shopApplyId, String checkNote);

    ResultResponse getShopApplyList(String shopName, Integer pageNum, Integer pageSize);
}
