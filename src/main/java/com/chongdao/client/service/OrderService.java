package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CartGoodsVo;
import com.chongdao.client.vo.OrderVo;
;

public interface OrderService {
    ResultResponse<CartGoodsVo> preOrder(CartGoodsVo cartGoodsVo);

    ResultResponse<OrderVo> createOrder(OrderVo orderVo);
}
