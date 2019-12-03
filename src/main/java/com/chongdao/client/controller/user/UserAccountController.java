package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.entitys.UserBank;
import com.chongdao.client.entitys.UserWithdrawal;
import com.chongdao.client.service.UserAccountService;
import com.chongdao.client.service.UserBankService;
import com.chongdao.client.service.UserTransService;
import com.chongdao.client.service.UserWithdrawalService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    @Autowired
    private UserWithdrawalService userWithdrawalService;
    @Autowired
    private UserBankService userBankService;

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

    /**
     * 申请提现
     * @param userWithdrawal
     * @return
     */
    @PostMapping("addUserWithdrawal")
    public ResultResponse addUserWithdrawal(@RequestBody UserWithdrawal userWithdrawal) {
        return userWithdrawalService.addUserWithdrawal(userWithdrawal);
    }

    /**
     * 获取我的提现记录
     * @param token
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getUserWithdrawalList")
    public ResultResponse getUserWithdrawalList(String token, Date startDate, Date endDate, Integer pageNum, Integer pageSize) { return userWithdrawalService.getUserWithdrawalList(token, null, null, null, startDate, endDate, pageNum, pageSize);}

    /**
     * 添加绑定用户银行卡
     * @param userBank
     * @return
     */
    @PostMapping("addUserBank")
    public ResultResponse addUserBank(@RequestBody UserBank userBank){
        return userBankService.addUserBankService(userBank);
    }

    /**
     * 获取我绑定的银行卡
     * @param userId
     * @return
     */
    @PostMapping("getUserBankList")
    public ResultResponse getUserBankList(Integer userId) {
        return userBankService.getUserBankList(userId);
    }
}
