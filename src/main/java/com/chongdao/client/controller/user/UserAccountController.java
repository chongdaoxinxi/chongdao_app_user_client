package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.service.UserAccountService;
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
@RequestMapping("/api/account")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    /**
     *
     * 获取用户账户信息
     * @param userId
     * @return
     */
    @GetMapping("/getUserAccount")
    public ResultResponse<UserAccount> getUserAccount(Integer userId) {
        return userAccountService.getUserAccountByUserId(userId);
    }

    /**
     * 保存用户账户信息
     * @param ua
     * @return
     */
    @GetMapping("/saveUserAccount")
    public ResultResponse saveUserAccount(UserAccount ua) {
        return userAccountService.saveUserAccount(ua);
    }
}
