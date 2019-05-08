package com.chongdao.client.controller.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.ShopService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop/")
public class ShopController {

    @Autowired
    private ShopService shopService;


    /**
     * 首页(分页获取所有商铺)
     * @param keyword 搜索关键词
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @param proActivities 1.满减活动 2.店铺打折 3.店铺红包
     */
    @GetMapping("list")
    public ResultResponse<PageInfo> list( @RequestParam(value = "keyword",required = false) String keyword,
                                          @RequestParam(value = "categoryId",required = false) String categoryId,
                                          @RequestParam(value = "proActivities",required = false) String  proActivities,
                                          @RequestParam(value = "orderBy",defaultValue = "arrangement",required = false) String orderBy,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){

        return shopService.list(keyword,categoryId,proActivities,orderBy,pageNum,pageSize);

    }


    /**
     * 根据Id获取店铺
     * @param shopId
     * @return
     */
    @GetMapping("get_shop_info")
    public ResultResponse getShopById(Integer shopId){
        return shopService.getShopById(shopId);
    }

    /**
     * 获取该店铺的服务和商品
     * @param shopId
     * @param categoryId 0 商品 1 服务
     * @return
     */
    @GetMapping("get_shop_type")
    public ResultResponse getShopService(Integer shopId, Integer categoryId){
        return shopService.getShopService(shopId,categoryId);
    }

}
