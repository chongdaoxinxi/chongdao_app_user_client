package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CartGoodsVo;
import com.chongdao.client.vo.OrderCommonVO;
import com.chongdao.client.vo.OrderVo;
;

public interface OrderService {
    ResultResponse<OrderVo> preOrCreateOrder(Integer userId, Integer addressId, OrderCommonVO orderCommonVO, Integer orderType);

   // ResultResponse<OrderVo> createOrder(OrderVo orderVo,OrderCommonVO orderCommonVO);
}
