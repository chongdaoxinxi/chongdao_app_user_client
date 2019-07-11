package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CartVo;

public interface CartsService {

    /**
     * 添加购物车
     * @param userId
     * @param count
     * @param goodsId
     * @return
     */
    ResultResponse<CartVo> add(Integer userId, Integer count, Integer goodsId,Integer shopId);


    /**
     * 再来一单（添加购物车）
     * @param userId
     * @param orderNo
     * @return
     */
    ResultResponse anotherAdd(Integer userId, String orderNo,Integer shopId);

    /**
     * 查询购物车
     * @param userId
     * @return
     */
    ResultResponse<CartVo> list(Integer userId,Integer shopId);

    /**
     * 删除购物车
     * @param userId
     * @param goodsIds
     * @return
     */
    ResultResponse<CartVo> deleteGoods(Integer userId, String goodsIds,Integer shopId);
}
