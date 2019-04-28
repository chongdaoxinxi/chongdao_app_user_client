package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;

public interface UserAccountService {

    ResultResponse<UserAccount> getUserAccountByUserId(Integer userId);

    ResultResponse saveUserAccount(UserAccount ua);
}
