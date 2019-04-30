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
     * @param userId
     * @param addressId
     * @return
     */
    @GetMapping("pre_order")
    public ResultResponse<CartGoodsVo> preOrder(String token, Integer userId, Integer addressId){
        //校验用户是否登录
        LoginUserUtil.resultTokenVo(token);
        return orderService.preOrder(userId,addressId);
    }

    @GetMapping("create_order")
    public ResultResponse<OrderVo> createOrder(String token, OrderVo orderVo){
        //校验用户是否登录
        LoginUserUtil.resultTokenVo(token);
        return orderService.createOrder(orderVo);
    }

}
