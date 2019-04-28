package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CartVo;

public interface OrderService {
    ResultResponse<CartVo> preOrder(CartVo cartVo);
}
