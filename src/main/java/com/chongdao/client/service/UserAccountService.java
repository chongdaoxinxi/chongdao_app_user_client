package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;

import java.math.BigDecimal;

/**
 * 用户账户接口
 */
public interface UserAccountService {

    ResultResponse<UserAccount> getUserAccountByUserId(Integer userId);

    ResultResponse saveUserAccount(UserAccount ua);

    /**
     * 用户充值
     * @param userId
     * @param money
     * @return
     */
    ResultResponse rechargeAccount(Integer userId,BigDecimal money);
}
