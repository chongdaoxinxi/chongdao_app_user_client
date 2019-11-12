package com.chongdao.client.controller.user;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Package;
import com.chongdao.client.entitys.User;
import com.chongdao.client.service.PackageService;
import com.chongdao.client.service.UserCardService;
import com.chongdao.client.service.UserService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.CardUserVo;
import com.chongdao.client.vo.ResultTokenVo;
import com.chongdao.client.vo.UserLoginVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCardService userCardService;
    @Autowired
    private PackageService packageService;

    /**
     * 用户登录接口
     * type 1:验证码登录 2::密码登录
     * @return
     */
    @PostMapping("login")
    public ResultResponse<UserLoginVO> login(@RequestParam("phone") String phone,String code, String password,@RequestParam("type") String type){
        return userService.login(phone, code,password,type);
    }

    /**
     * 用户设置密码接口
     * @return
     */
    @PostMapping("settingPwd")
    public ResultResponse<User> settingPwd(@RequestParam("userId") Integer userId,String password, String confirmPassword,String newPassword){
        return userService.settingPwd(userId,password,confirmPassword,newPassword);
    }

    /**
     * 重置密码
     * @param phone
     * @param code
     * @param password
     * @param confirmPassword
     * @return
     */
    @PostMapping("resetPwd")
    public ResultResponse<User> resetPwd(@RequestParam("phone") String phone, @RequestParam("code")String code, @RequestParam("password") String password, @RequestParam("confirmPassword")String confirmPassword){
        return userService.resetPwd(phone,code,password,confirmPassword);
    }

    /**
     * 用户通过推广链接完成注册
     * @return
     */
    @PostMapping("recommendSign")
    public ResultResponse recommendSign(String phone, Integer type, Integer recommendId, String code) {
        return userService.saveUserRecommendData(phone, type, recommendId, code);
    }

    /**
     * 获取用户优惠券
     * @param token
     * @param type 1:优惠券;2:配送券
     * @return
     */
    @GetMapping("getUserCard")
    public ResultResponse<PageInfo<CardUserVo>> getUserCard(String token, Integer type, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return userCardService.getUserCard(tokenVo.getUserId(), type, pageNum, pageSize);
    }

    /**
     * 获取礼包列表
     * @return
     */
    @GetMapping("getPackage")
    public ResultResponse<List<Package>> getPackage() {
        return packageService.getPackageList();
    }

    @PostMapping("checkSmsCode")
    public ResultResponse checkSmsCode(String phone, String code) {
        return userService.checkSmsCode(phone, code);
    }
}
