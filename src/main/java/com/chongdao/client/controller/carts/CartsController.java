package com.chongdao.client.controller.carts;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.CartsService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.CartVo;
import com.chongdao.client.vo.ResultTokenVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart/")
@Slf4j
public class CartsController {

    @Autowired
    private CartsService cartsService;

    /**
     * 添加到购物车
     * @param count
     * @param goodsId
     * @return
     */
    @PostMapping("add")
    public ResultResponse<CartVo> add(String token,Integer count, Integer goodsId,Integer shopId){
        //检验该用户的token
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return cartsService.add(tokenVo.getUserId(),count,goodsId,shopId);
    }


    /**
     * 查询购物车
     * @param token
     * @return
     */
    @GetMapping("list")
    public ResultResponse<CartVo> list(String token,Integer shopId){
        //检验该用户的token
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return cartsService.list(tokenVo.getUserId(),shopId);
    }


    /**
     * 删除购物车
     * @param token
     * @param goodsIds
     * @return
     */
    @DeleteMapping("delete")
    public ResultResponse<CartVo> deleteGoods(String token, String goodsIds,Integer shopId){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return cartsService.deleteGoods(tokenVo.getUserId(),goodsIds,shopId);
    }



}
