package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CartGoodsVo;
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
}
