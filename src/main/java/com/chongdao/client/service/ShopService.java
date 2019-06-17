package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Shop;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface ShopService{
    /**
     * 首页商铺展示
     * @param categoryId
     * @param proActivities
     * @param orderBy
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> list( String categoryId, String  proActivities, String orderBy, int pageNum, int pageSize);

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

    /**
     * 获取店铺所有订单评价以及店铺总评价
     * @param shopId
     * @return
     */
    ResultResponse getShopEvalAll(Integer shopId);

    /**
     * 更新商店余额
     * @param shopId
     * @param money
     * @return
     */
    ResultResponse updateShopMoney(Integer shopId, BigDecimal money);

    /**
     * 搜索店铺
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Shop> pageQuery(String keyword, int pageNum, int pageSize);
}
