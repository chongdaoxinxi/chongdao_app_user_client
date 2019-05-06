package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/6
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/favourite")
public class UserFavouriteController {
    @Autowired
    private UserService userService;

    /**
     * 获取我的店铺收藏列表
     * @param userId
     * @return
     */
    @GetMapping("/getFavouriteShopList")
    public ResultResponse getFavouriteShopList(Integer userId) {
        return userService.getFavouriteShopList(userId);
    }

    /**
     * 获取我的店铺收藏列表
     * @param userId
     * @return
     */
    @GetMapping("/getFavouriteGoodList")
    public ResultResponse getFavouriteGoodList(Integer userId) {
        return userService.getFavouriteGoodList(userId);
    }
}
