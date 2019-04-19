package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.User;
import com.chongdao.client.vo.UserLoginVO;

public interface UserService {
    /**
     * 用户登录接口
     * @return
     */
    ResultResponse<UserLoginVO> login(UserLoginVO userLoginVO);

    ResultResponse<String> register(UserLoginVO userLoginVO);
}
