package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.entitys.UserTrans;

import java.math.BigDecimal;

public interface UserTransService {
    void saveUserTarns(UserTrans ut);

    /**
     * 添加一笔用户金额操作记录, 同时调用改变余额的方法
     * @param money
     * @param comment
     */
    void addUserTrans(Integer userId, BigDecimal money, String comment, Integer type);

    void saveUserTransByRecharge(UserAccount ua, BigDecimal money);

    ResultResponse getUserTrans(Integer userId, Integer type, Integer pageNum, Integer pageSize);
}
