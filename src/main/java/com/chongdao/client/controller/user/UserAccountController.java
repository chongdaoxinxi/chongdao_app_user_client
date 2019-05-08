package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.entitys.UserTrans;
import com.chongdao.client.repository.UserTransRepository;
import com.chongdao.client.service.UserAccountService;
import com.chongdao.client.service.UserTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户账户信息
 * @Author onlineS
 * @Date 2019/5/6
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/account")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserTransService userTransService;

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

    /**
     *
     * @param userId
     * @param type 1:用户充值;2:订单消费;3:订单退款
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getUserTrans")
    public ResultResponse<Page<UserTrans>> getUserTrans(Integer userId, Integer type, Integer pageNum, Integer pageSize) {
        return userTransService.getUserTrans(userId, type, pageNum, pageSize);
    }
}
