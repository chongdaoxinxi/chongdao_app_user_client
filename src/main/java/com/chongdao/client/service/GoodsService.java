package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
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
    ResultResponse<PageInfo> getGoodsByKeyword(String keyword, int pageNum, int pageSize, String categoryId, String orderBy);
}
