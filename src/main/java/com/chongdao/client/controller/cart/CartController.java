package com.chongdao.client.controller.cart;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.service.CartService;
import com.chongdao.client.utils.JsonUtil;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.CartVo;
import com.chongdao.client.vo.ResultTokenVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/cart/")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加到购物车
     * @param count
     * @param goodsId
     * @return
     */
    @GetMapping("add")
    public ResultResponse<CartVo> add(String token,Integer count, Integer goodsId){
        //检验该用户的token
        //将map转化为ResultTokenVo
        ResultTokenVo tokenVo = JsonUtil.map2Obj(TokenUtil.validateToken(token), ResultTokenVo.class);
        //如果返回是200代表用户已登录，否则未登录或者失效
        if (tokenVo.getUserId() == null){
            return ResultResponse.createByErrorCodeMessage(tokenVo.getStatus(),tokenVo.getMessage());
        }
        return cartService.add(tokenVo.getUserId(),count,goodsId);
    }


    /**
     * 查询购物车
     * @param token
     * @return
     */
    @GetMapping("list")
    public ResultResponse<CartVo> list(String token){
        //检验该用户的token
        //将map转化为ResultTokenVo
        ResultTokenVo tokenVo = JsonUtil.map2Obj(TokenUtil.validateToken(token), ResultTokenVo.class);
        //如果返回是200代表用户已登录，否则未登录或者失效
        //登录失败
        if (tokenVo.getUserId() == null){
            return ResultResponse.createByErrorCodeMessage(tokenVo.getStatus(),tokenVo.getMessage());
        }
        return cartService.list(tokenVo.getUserId());
    }


    /**
     * 删除购物车
     * @param token
     * @param goodsIds
     * @return
     */
    @RequestMapping("delete_goods")
    public ResultResponse<CartVo> deleteGoods(String token, String goodsIds){
        ResultTokenVo tokenVo = JsonUtil.map2Obj(TokenUtil.validateToken(token), ResultTokenVo.class);
        //如果返回是200代表用户已登录，否则未登录或者失效
        if (tokenVo.getUserId() == null){
            return ResultResponse.createByErrorCodeMessage(tokenVo.getStatus(),tokenVo.getMessage());
        }
        return cartService.deleteGoods(tokenVo.getUserId(),goodsIds);
    }



}
