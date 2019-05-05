package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CartGoodsVo;
import com.chongdao.client.vo.OrderCommonVO;
import com.chongdao.client.vo.OrderVo;
;

public interface OrderService {
    ResultResponse<OrderVo> preOrCreateOrder(Integer userId, OrderCommonVO orderCommonVO, Integer orderType);

    /**
     * 根据type 获取订单列表
     * @param userId
     * @param type
     * @return
     */
    ResultResponse getOrderTypeList(Integer userId, String type);

    // ResultResponse<OrderVo> createOrder(OrderVo orderVo,OrderCommonVO orderCommonVO);
}
