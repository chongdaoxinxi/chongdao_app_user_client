package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.OrderCommonVO;
import com.chongdao.client.vo.OrderVo;
import com.github.pagehelper.PageInfo;

;

public interface OrderService {
    ResultResponse<OrderVo> preOrCreateOrder(Integer userId, OrderCommonVO orderCommonVO, Integer orderType);

    /**
     * 根据type 获取订单列表
     * @param userId
     * @param type
     * @return
     */
    ResultResponse<PageInfo> getOrderTypeList(Integer userId, String type, int pageNum, int pageSize);

    // ResultResponse<OrderVo> createOrder(OrderVo orderVo,OrderCommonVO orderCommonVO);

    ResultResponse<PageInfo> getShopOrderTypeList(Integer shopId, String type, Integer pageNum, Integer pageSize);

    /**
     * 拒单
     * @param orderId
     * @return
     */
    ResultResponse shopRefuseOrder(Integer orderId);

    /**
     * 退款
     * @param orderId
     * @return
     */
    ResultResponse shopRefundOrder(Integer orderId);

    /**
     * 商家手动接单
     * @param orderId
     * @return
     */
    ResultResponse shopAcceptOrder(Integer orderId);

    /**
     * 商家服务完成(->用户+配送员)
     * @param orderId
     * @return
     */
    ResultResponse shopServiceCompleted(Integer orderId);

    /**
     * 获取普通配送员订单
     * @param expressId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> expressOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize);

    /**
     * 获取管理员配送员订单
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> expressAdminOrderList(String type, Integer pageNum, Integer pageSize);
}
