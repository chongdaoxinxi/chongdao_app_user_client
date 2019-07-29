package com.chongdao.client.controller.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.ShopService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop/")
@CrossOrigin
public class ShopController {

    @Autowired
    private ShopService shopService;


    /**
     * 首页(分页获取所有商铺)
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @param proActivities 1.满减活动 2.店铺打折 3.店铺红包
     */
    @GetMapping("list")
    public ResultResponse<PageInfo> list(@RequestParam(value = "categoryId",required = false) String categoryId,
                                         @RequestParam(value = "userId",required = false) Integer userId,
                                          @RequestParam(value = "proActivities",required = false) String  proActivities,
                                          @RequestParam(value = "orderBy",defaultValue = "arrangement",required = false) String orderBy,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){

        return shopService.list(userId,categoryId,proActivities,orderBy,pageNum,pageSize);

    }

    @GetMapping("search")
    public ResultResponse pageQuery(String keyword,
                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return ResultResponse.createBySuccess(shopService.pageQuery(keyword,pageNum,pageSize));

    }


    /**
     * 根据Id获取店铺
     * @param shopId
     * @return
     */
    @GetMapping("{shopId}")
    public ResultResponse getShopById(@PathVariable Integer shopId){
        return shopService.getShopById(shopId);
    }

    /**
     * 获取该店铺的服务和商品
     * @param shopId
     * @param categoryId 0 商品 1 服务
     * @return
     */
    @GetMapping("{shopId}/{categoryId}")
    public ResultResponse getShopService(@PathVariable Integer shopId,
                                         @PathVariable Integer categoryId,String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopService.getShopService(shopId,categoryId,tokenVo.getUserId());
    }


    /**
     * 获取店铺所有订单评价以及店铺总评价
     * @param shopId
     * @return
     */
    @GetMapping("getEvalAll/{shopId}")
    public ResultResponse getShopEvalAll(@PathVariable Integer shopId){
        return shopService.getShopEvalAll(shopId);
    }


    /**
     * 关注店铺/取消关注
     * @param shopId
     * @return
     */
    @PostMapping
    public ResultResponse concernShop(@RequestParam Integer shopId,@RequestParam Integer status,String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopService.concernShop(tokenVo.getUserId(),shopId,status);
    }

    /**
     * 查看关注店铺列表
     * @param token
     * @return
     */
    @GetMapping
    public ResultResponse queryConcernShopList(String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopService.queryConcernShopList(tokenVo.getUserId());
    }

}
