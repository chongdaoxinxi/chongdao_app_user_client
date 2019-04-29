package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CartGoodsVo;
;

public interface OrderService {
    ResultResponse<CartGoodsVo> preOrder(CartGoodsVo cartGoodsVo);
}
