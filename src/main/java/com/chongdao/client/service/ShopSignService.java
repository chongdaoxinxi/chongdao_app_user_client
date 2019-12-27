package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.ShopSignInfo;

public interface ShopSignService {
    /**
     * 申请商家入驻
     * @param shopSignInfo
     * @return
     */
    ResultResponse applyShopSign(ShopSignInfo shopSignInfo);
}
