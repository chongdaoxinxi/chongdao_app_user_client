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
    ResultResponse<PageInfo> list( Integer userId,String categoryId, String  proActivities, String orderBy, Double lng,Double lat,int pageNum, int pageSize);

    /**
     * 获取商店列表(管理员)
     * @param managementId
     * @param shopName
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> getShopDataList(Integer managementId, String shopName, Integer pageNum, Integer pageSize);

    /**
     * 获取店铺
     * @param shopId
     * @return
     */
    ResultResponse getShopById(Integer shopId);

    ResultResponse addShop(Shop shop);

    /**
     * 获取店铺商品
     * @param shopId
     * @param type 0 商品 1 服务
     * @return
     */
    ResultResponse getShopService(Integer shopId, Integer type,Integer userId);

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

    /**
     * 关注店铺
     * @param userId
     * @param shopId
     * @return
     */
    ResultResponse concernShop(Integer userId, Integer shopId,Integer status);

    /**
     * 查看关注店铺列表
     * @param userId
     * @return
     */
    ResultResponse queryConcernShopList(Integer userId);
}
