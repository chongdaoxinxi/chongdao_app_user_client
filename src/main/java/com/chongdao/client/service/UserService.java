package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.UserLoginVO;
import com.chongdao.client.vo.UserSettingVO;

public interface UserService {
    /**
     * 用户登录接口
     * @return
     */
    ResultResponse<UserLoginVO> login(String phone, String code);

    //ResultResponse<String> register(UserLoginVO userLoginVO);

    ResultResponse<UserSettingVO> getUserSettingInfo(Integer userId);

    ResultResponse saveUserSetting(UserSettingVO uso);
}
