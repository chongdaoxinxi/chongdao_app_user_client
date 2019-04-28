package com.chongdao.client.controller.order;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.JsonUtil;
import com.chongdao.client.utils.TokenUtil;
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
     * @param cartVo
     * @return
     */
    @GetMapping("pre_order")
    public ResultResponse<CartVo> preOrder(String token,CartVo cartVo){
        //检验该用户的token
        //将map转化为ResultTokenVo
        ResultTokenVo tokenVo = JsonUtil.map2Obj(TokenUtil.validateToken(token), ResultTokenVo.class);
        //如果返回是200代表用户已登录，否则未登录或者失效
        //登录失败
        if (tokenVo.getUserId() == null){
            return ResultResponse.createByErrorCodeMessage(tokenVo.getStatus(),tokenVo.getMessage());
        }
        return orderService.preOrder(cartVo);
    }
}
