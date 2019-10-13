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
@CrossOrigin
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
    public ResultResponse<CartVo> add(@RequestParam String token,@RequestParam Integer count,
                                      @RequestParam Integer goodsId,@RequestParam Integer shopId,
                                      Integer petId,Integer petCount){
        //检验该用户的token
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return cartsService.add(tokenVo.getUserId(),count,goodsId,shopId,petId,petCount);
    }


    /**
     * 查询购物车
     * @param userId
     * @return
     */
    @GetMapping("list")
    public ResultResponse<CartVo> list(Integer userId,@RequestParam Integer shopId){
        return cartsService.list(userId,shopId);
    }


    /**
     * 删除购物车
     * @param token
     * @param goodsIds
     * @return
     */
    @PostMapping("delete")
    public ResultResponse<CartVo> deleteGoods(@RequestParam String token,@RequestParam Integer goodsIds,
                                              @RequestParam Integer shopId,Integer petId){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return cartsService.deleteGoods(tokenVo.getUserId(),goodsIds,shopId,petId);
    }


    /**
     * 清空购物车
     * @param token
     * @param shopId
     * @return
     */
    @PostMapping("clear")
    public ResultResponse<CartVo> clear(@RequestParam String token,@RequestParam Integer shopId){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return cartsService.clear(tokenVo.getUserId(),shopId);
    }



}
