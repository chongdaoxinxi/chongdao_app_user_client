package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CouponVO;
import com.chongdao.client.vo.GoodsDetailVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface GoodsService {


    /**
     * 商品列表展示
     * @param keyword 搜索关键词
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @return
     */
    ResultResponse<PageInfo> getGoodsByKeyword(String keyword, int pageNum, int pageSize, String categoryId,String  proActivities, String orderBy);

    /**
     * 商品详情
     * @param goodsId
     * @return
     */
    ResultResponse<GoodsDetailVo> getGoodsDetail(Integer goodsId);

    /**
     * 商品详情中的优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(1：商品 2: 服务)
     * @return
     */
    ResultResponse<List<CouponVO>> getCouponListByShopIdAndType(Integer shopId, Integer type);
}
