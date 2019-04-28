package com.chongdao.client.controller.goods;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Good;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.vo.CouponVO;
import com.chongdao.client.vo.GoodsDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/goods/")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    /**
     * 商品列表展示
     * @param keyword 搜索关键词
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @param categoryId  筛选条件(商品分类)
     * @param  proActivities 筛选条件(优惠活动)
     * @return
     */
    @GetMapping("list")
    public ResultResponse<PageInfo> list(@RequestParam(value = "keyword",required = false) String keyword,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "categoryId",required = false) String categoryId,
                                         @RequestParam(value = "proActivities",required = false) String  proActivities,
                                         @RequestParam(value = "orderBy",defaultValue = "arrangement",required = false) String orderBy){

        return goodsService.getGoodsByKeyword(keyword,pageNum,pageSize,categoryId,proActivities,orderBy);
    }


    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    @GetMapping("getGoodsDetail")
    public ResultResponse<GoodsDetailVo>  getGoodsDetail(Integer goodsId){
        return goodsService.getGoodsDetail(goodsId);
    }


    /**
     * 商品详情中的优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(1：商品 2: 服务)
     * @return
     */
    @GetMapping("getCouponListByShopId")
    public ResultResponse<List<CouponVO>> getCouponListByShopId(Integer shopId, Integer type){
        return goodsService.getCouponListByShopIdAndType(shopId, type);
    }
}
