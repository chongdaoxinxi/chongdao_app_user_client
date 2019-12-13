package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/12/11
 * @Version 1.0
 **/
public interface UserRegInfoService {
    ResultResponse addUserRegInfo(Integer userId, String regId, String alias, String userAccount);
}
