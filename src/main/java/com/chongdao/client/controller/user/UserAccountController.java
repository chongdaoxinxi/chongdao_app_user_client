package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.service.UserAccountService;
import com.chongdao.client.service.UserTransService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/account/")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserTransService userTransService;

    /**
     *
     * 获取用户账户信息
     * @param token
     * @return
     */
    @GetMapping("getUserAccount")
    public ResultResponse<UserAccount> getUserAccount(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return userAccountService.getUserAccountByUserId(tokenVo.getUserId());
    }

    /**
     * 保存用户账户信息
     * @param ua
     * @return
     */
    @GetMapping("saveUserAccount")
    public ResultResponse saveUserAccount(UserAccount ua) {
        return userAccountService.saveUserAccount(ua);
    }

    /**
     *
     * @param token
     * @param type 1:用户充值;2:订单消费;3:订单退款
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getUserTrans")
    public ResultResponse getUserTrans(String token, Integer type, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return userTransService.getUserTrans(tokenVo.getUserId(), type, pageNum, pageSize);
    }
}
