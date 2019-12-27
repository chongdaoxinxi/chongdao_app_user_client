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

    /**
     * 获取店铺类型
     * @return
     */
    ResultResponse getShopType();

    /**
     * 获取我的注册列表
     * @return
     */
    ResultResponse getMySignList(Integer userId);
}
