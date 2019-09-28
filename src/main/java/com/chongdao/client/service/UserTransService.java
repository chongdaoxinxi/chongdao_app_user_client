package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.entitys.UserTrans;

import java.math.BigDecimal;

public interface UserTransService {
    void saveUserTarns(UserTrans ut);

    void saveUserTransByRecharge(UserAccount ua, BigDecimal money);

    ResultResponse getUserTrans(Integer userId, Integer type, Integer pageNum, Integer pageSize);
}
