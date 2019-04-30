package com.chongdao.client.controller.cart;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.CartService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.CartVo;
import com.chongdao.client.vo.ResultTokenVo;
import lombok.extern.slf4j.Slf4j;
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
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
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
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
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
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return cartService.deleteGoods(tokenVo.getUserId(),goodsIds);
    }



}
