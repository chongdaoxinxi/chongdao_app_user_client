package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.service.ShopManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shopManage/")
public class ShopManageController {
    @Autowired
    private ShopManageService shopManageService;

    /**
     * 商家注册
     * @return
     */
    public ResultResponse shopSign() {
        return null;
    }

    /**
     * 商家登录
     * @return
     */
    @GetMapping("/shopLogin")
    public ResultResponse shopLogin(String name, String password) {
        return shopManageService.shopLogin(name, password);
    }

    /**
     * 获取店铺基本信息
     * @param shopId
     * @return
     */
    public ResultResponse getShopInfo(Integer shopId) {
        return null;
    }

    /**
     * 保存店铺信息
     * @param s
     * @return
     */
    public ResultResponse saveShopIndo(Shop s) {
        return null;
    }

    /**
     * 获取店铺销售统计
     * @param shopId
     * @return
     */
    public ResultResponse getShopOrderStatistics(Integer shopId) {
        return null;
    }
}
