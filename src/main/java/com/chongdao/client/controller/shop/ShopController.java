package com.chongdao.client.controller.shop;

import com.chongdao.client.common.GuavaCache;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.ShopSignInfo;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.service.ShopService;
import com.chongdao.client.service.ShopSignService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/shop/")
@CrossOrigin
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ShopSignService shopSignService;

    /**
     * 首页(分页获取所有商铺)
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @param proActivities 1.满减活动 0.店铺打折 3.店铺红包
     */
    @GetMapping("list")
    public ResultResponse<PageInfo> list(@RequestParam(value = "categoryId",required = false) String categoryId,
                                         @RequestParam(value = "userId",required = false) Integer userId,
                                          @RequestParam(value = "proActivities",required = false) String  proActivities,
                                          @RequestParam(value = "orderBy",defaultValue = "arrangement",required = false) String orderBy,
                                          @RequestParam(value = "lng") Double lng, @RequestParam("lat") Double lat,
                                          @RequestParam(value = "areaCode") String areaCode,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return shopService.list(userId, categoryId, proActivities, orderBy, lng, lat, areaCode, pageNum, pageSize);

    }

    /**
     * 地图商家数据
     * @param lng
     * @param lat
     * @return
     */
    @GetMapping("list/geo")
    public ResultResponse listGeo(@RequestParam(value = "lng") Double lng, @RequestParam("lat") Double lat, @RequestParam String areaCode){
        ResultResponse resultResponse = (ResultResponse<PageInfo>) GuavaCache.getKey("home_shop_list_geo");
        BigDecimal newLat = BigDecimal.valueOf(lat).setScale(3, BigDecimal.ROUND_HALF_UP);
        BigDecimal newLng = BigDecimal.valueOf(lng).setScale(3, BigDecimal.ROUND_HALF_UP);
        if (resultResponse != null){
            BigDecimal cacheLat = (BigDecimal) GuavaCache.getKey("list_geo_lat");
            BigDecimal cacheLng = (BigDecimal) GuavaCache.getKey("list_geo_lng");
            if (cacheLat.compareTo(newLat) != 0 &&  cacheLng.compareTo(newLng) != 0) {
                resultResponse = shopService.listGeo(lng, lat, areaCode);
                GuavaCache.setKey("home_shop_list_geo", resultResponse);
                GuavaCache.setKey("list_geo_lat", newLat);
                GuavaCache.setKey("list_geo_lng", newLng);
                return resultResponse;
            }
            return resultResponse;
        }
        resultResponse = shopService.listGeo(lng, lat, areaCode);
        GuavaCache.setKey("home_shop_list_geo", resultResponse);
        GuavaCache.setKey("list_geo_lat", newLat);
        GuavaCache.setKey("list_geo_lng", newLng);
        return resultResponse;

    }

    @GetMapping("search")
    public ResultResponse pageQuery(String keyword,
                                    @RequestParam String areaCode,
                                    @RequestParam(value = "categoryId",required = false) String categoryId,
                                    @RequestParam(value = "userId",required = false) Integer userId,
                                    @RequestParam(value = "proActivities",required = false) String  proActivities,
                                    @RequestParam(value = "orderBy",defaultValue = "arrangement",required = false) String orderBy,
                                    @RequestParam(value = "lng") Double lng, @RequestParam("lat") Double lat){
        return ResultResponse.createBySuccess(shopService.pageQuery(keyword,areaCode,categoryId,userId,proActivities,orderBy,lng,lat));

    }


    /**
     * 根据Id获取店铺
     * @param shopId
     * @return
     */
    @GetMapping("{shopId}")
    public ResultResponse getShopById(@PathVariable Integer shopId, @RequestParam Double lat, @RequestParam Double lng,@RequestParam Integer userId){
        if (lat == null || lng == null) {
            return ResultResponse.createByErrorCodeMessage(400, "经纬度不能为空");
        }
        return shopService.getShopById(shopId,lat,lng,userId);
    }

    /**
     * 获取店铺分类
     * @param categoryId 0 商品 1 服务
     * @return
     */
    @GetMapping("getCategoryList/{categoryId}")
    public ResultResponse getGoodCategoryList(@PathVariable Integer categoryId) {
        return goodsService.getShopGoodsCategory(categoryId);
    }




    /**
     * 根据分类商品id获取该店铺的服务和商品
     * @param shopId
     * @param goodsTypeId
     * @return
     */
    @GetMapping("getShopGoodsList")
    public ResultResponse getShopGoodsList(@RequestParam Integer shopId,
                                         @RequestParam Integer goodsTypeId,Integer userId){
//        ResultResponse resultResponse = (ResultResponse) GuavaCache.getKey("getShopGoods_" + shopId + "_" + goodsTypeId);
//        if (resultResponse != null){
//            return resultResponse;
//        }
//        resultResponse = this.shopService.getShopService(shopId, goodsTypeId, userId);
//        GuavaCache.setKey("getShopGoods_" + shopId + "_" + goodsTypeId, resultResponse);
        return this.shopService.getShopService(shopId,goodsTypeId,userId);
    }


    /**
     * 获取店铺所有订单评价以及店铺总评价
     * @param shopId
     * @return
     */
    @GetMapping("getEvalAll/{shopId}")
    public ResultResponse getShopEvalAll(@PathVariable Integer shopId){
        ResultResponse response = (ResultResponse) GuavaCache.getKey("getEvalAll" + shopId);
        if (response != null){
            return response;
        }
        response = shopService.getShopEvalAll(shopId);
        GuavaCache.setKey("getEvalAll" + shopId, response);
        return shopService.getShopEvalAll(shopId);
    }


    /**
     * 关注店铺/取消关注
     * @param shopId
     * @return
     */
    @PostMapping
    public ResultResponse concernShop(@RequestParam Integer shopId,@RequestParam String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopService.concernShop(tokenVo.getUserId(),shopId);
    }

    /**
     * 查看关注店铺列表
     * @param token
     * @return
     */
    @GetMapping
    public ResultResponse queryConcernShopList(@RequestParam String token,@RequestParam Double lng,@RequestParam Double lat){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopService.queryConcernShopList(tokenVo.getUserId(),lng,lat);
    }

    ///////////////////////////////////////商家入驻///////////////////////////////////////////

    /**
     * 申请商家入驻
     * @param shopSignInfo
     * @return
     */
    @PostMapping("applyShopSign")
    public ResultResponse applyShopSign(@RequestBody ShopSignInfo shopSignInfo) {
        return shopSignService.applyShopSign(shopSignInfo);
    }

    /**
     * 获取店铺类型
     * @return
     */
    @PostMapping("getShopType")
    public ResultResponse getShopType() {
        return shopSignService.getShopType();
    }

    /**
     * 获取我的注册列表
     * @param userId
     * @return
     */
    @PostMapping("getMySignList")
    public ResultResponse getMySignList(Integer userId) {
        return shopSignService.getMySignList(userId);
    }
}
