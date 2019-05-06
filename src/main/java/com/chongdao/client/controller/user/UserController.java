package com.chongdao.client.controller.user;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.*;
import com.chongdao.client.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chongdao.client.entitys.Package;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCardService userCardService;
    @Autowired
    private PackageService packageService;

    /**
     * 用户登录接口
     * @return
     */
    @GetMapping("/login")
    public ResultResponse<UserLoginVO> login(String phone, String code){
        return userService.login(phone, code);
    }

    /**
     * 获取用户优惠券
     * @param userId
     * @param type 1:优惠券;2:配送券
     * @return
     */
    @GetMapping("/getUserCard")
    public ResultResponse getUserCard(Integer userId, Integer type) {
        return userCardService.getUserCard(userId, type);
    }

    /**
     * 获取礼包列表
     * @return
     */
    @GetMapping("/getPackage")
    public ResultResponse<List<Package>> getPackage() {
        return packageService.getPackageList();
    }
}
