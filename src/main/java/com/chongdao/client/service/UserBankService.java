package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserBank;

public interface UserBankService {
    ResultResponse addUserBankService(UserBank userBank);

    ResultResponse getUserBankList(Integer userId);
}
