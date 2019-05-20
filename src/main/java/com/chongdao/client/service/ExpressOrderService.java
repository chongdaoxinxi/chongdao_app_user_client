package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.github.pagehelper.PageInfo;

/**
 * 配送端获取订单列表接口
 */
public interface ExpressOrderService {
    /**
     * 获取订单列表(可接, 已接, 已完成)
     * @param expressId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> getExpressOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize);

    /**
     * 获取配送管理员订单列表(商家已接单, 商家未接单)
     * @param expressId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> getExpressAdminOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize);

    /**
     * 接单
     * @param expressId
     * @param orderId
     * @return
     */
    ResultResponse expressAcceptOrder(Integer expressId, Integer orderId);

    /**
     * 取消订单(状态变为-1)-----------疑问 此方法存在合理嘛
     * @param expressId
     * @param orderId
     * @return
     */
    ResultResponse cancelOrder(Integer expressId, Integer orderId);

    /**
     * 到店
     * @param expressId
     * @param orderId
     * @return
     */
    ResultResponse arriveShop(Integer expressId, Integer orderId);

    /**
     * 服务完成
     * @param expressId
     * @param orderId
     * @return
     */
    ResultResponse serviceComplete(Integer expressId, Integer orderId);
}
