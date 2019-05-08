package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.github.pagehelper.PageInfo;

public interface ShopService{
    /**
     * 首页商铺展示
     * @param keyword
     * @param categoryId
     * @param proActivities
     * @param orderBy
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> list(String keyword, String categoryId, String  proActivities, String orderBy, int pageNum, int pageSize);

    /**
     * 获取店铺
     * @param shopId
     * @return
     */
    ResultResponse getShopById(Integer shopId);


    /**
     * 获取店铺商品
     * @param shopId
     * @param type 0 商品 1 服务
     * @return
     */
    ResultResponse getShopService(Integer shopId, Integer type);
}
