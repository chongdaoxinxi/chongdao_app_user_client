package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.service.ShopService;
import com.chongdao.client.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shop_good_manage/")
public class ShopGoodManageController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 获取商品类别
     * @param token
     * @return
     */
    @GetMapping("/get_good_category_list")
    public ResultResponse getGoodCategoryList(String token, Integer shopId) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.getGoodCategoryList(shopId);
    }

    /**
     * 获取商品列表
     * @param token
     * @param goodsTypeId
     * @param goodName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("get_good_list")
    public ResultResponse getGoodList(String token,
                                      @RequestParam(value = "shopId") Integer shopId,
                                      @RequestParam(required = false) Integer goodsTypeId,
                                      @RequestParam(required = false) Integer goodName,
                                      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.getGoodList(shopId,goodsTypeId,goodName,pageNum,pageSize);
    }

    /**
     * 商品下架
     * @param goodId
     * @param status 1:上架,0下架，-1删除
     * @return
     */
    @GetMapping("update_goods_status")
    public ResultResponse offShelveGood(String token,Integer goodId,Integer status) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.updateGoodsStatus(goodId, status);
    }


    /**
     * 打折商品
     * @param token
     * @param goodsTypeId
     * @return
     */
    @GetMapping("goods_discount")
    public ResultResponse discountGood(String token,Integer shopId, Integer goodsTypeId, Double discount) {
        LoginUserUtil.resultTokenVo(token);
        return goodsService.discountGood(shopId,goodsTypeId,discount);
    }
}
