package com.chongdao.client.controller.order;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.JsonUtil;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.CartGoodsVo;
import com.chongdao.client.vo.CartVo;
import com.chongdao.client.vo.OrderVo;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 预下单
     * @param cartGoodsVo
     * @return
     */
    @GetMapping("pre_order")
    public ResultResponse<CartGoodsVo> preOrder(String token, CartGoodsVo cartGoodsVo){
        //校验用户是否登录
        LoginUserUtil.resultTokenVo(token);
        return orderService.preOrder(cartGoodsVo);
    }
}
