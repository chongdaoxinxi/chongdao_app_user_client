package com.chongdao.client.controller.order;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.JsonUtil;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.*;
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
     * 预下单/提交订单
     * @param userId
     * @param orderType 1代表预下单 2代表下单
     * @return
     */
    @GetMapping("pre_or_create_order")
    public ResultResponse<OrderVo> preOrCreateOrder(String token,Integer userId, OrderCommonVO orderCommonVO, Integer orderType){
        //校验用户是否登录
        LoginUserUtil.resultTokenVo(token);
        return orderService.preOrCreateOrder(userId,orderCommonVO,orderType);
    }


}
