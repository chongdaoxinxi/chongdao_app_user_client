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




    //-------------------------------------------------------商户端接口-------------------------------------------------------------------------------------//

    /**
     * 获取商品类别
     * @return
     */
    ResultResponse getGoodCategoryList(Integer shopId);

    /**
     * 获取商品列表
     * @param goodsTypeId
     * @param goodName
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse getGoodList(Integer shopId,Integer goodsTypeId, Integer goodName, int pageNum, int pageSize);


    /**
     * 商品下架
     * @param goodId
     * @param status 1:上架,0下架，-1删除
     * @return
     */
    ResultResponse updateGoodsStatus(Integer goodId, Integer status);


    /**
     * 商品打折
     * @param goodsTypeId
     * @param discount
     * @return
     */
    ResultResponse discountGood(Integer shopId,Integer goodsTypeId, Double discount);
}
