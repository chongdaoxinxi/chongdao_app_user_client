package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;

/**
 * 用户账户接口
 */
public interface UserAccountService {

    ResultResponse<UserAccount> getUserAccountByUserId(Integer userId);

    ResultResponse saveUserAccount(UserAccount ua);
}
