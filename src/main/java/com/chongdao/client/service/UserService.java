package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.response.UserResponse;

public interface UserService {
    /**
     * 用户登录接口
     * @param name
     * @param password
     * @return
     */
    ResultResponse<UserResponse> login(String name, String password);
}
