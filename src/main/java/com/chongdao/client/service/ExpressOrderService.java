package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

/**
 * 配送端获取订单列表接口
 */
public interface ExpressOrderService {
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

    /**
     * 单程(店->家), 配送员到店后开始配送时的短信通知
     * @param expressId
     * @param orderId
     * @return
     */
    ResultResponse expressStartServiceInSingleTripNotice(Integer expressId, Integer orderId);
}
