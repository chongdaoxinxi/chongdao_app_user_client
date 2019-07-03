package com.chongdao.client.controller.goods;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Category;
import com.chongdao.client.repository.CategoryRepository;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.vo.GoodsDetailVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/goods/")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryRepository categoryRepository;


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
    @GetMapping("getGoodsDetail/{goodsId}")
    public ResultResponse<GoodsDetailVo>  getGoodsDetail(@PathVariable Integer goodsId){
        return goodsService.getGoodsDetail(goodsId);
    }


    /**
     * 筛选商品分类
     * @return
     */
    @GetMapping("getCategory")
    public ResultResponse getCategory(){
        List<Category> categoryList = categoryRepository.findAllByStatus(1);
        return ResultResponse.createBySuccess(categoryList);
    }









}
