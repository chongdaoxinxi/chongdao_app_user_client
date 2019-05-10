package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserShare;

public interface UserShareService {
    UserShare getUserShareByUserId(Integer userId);

    UserShare saveUserShare(UserShare us);

    ResultResponse userShare(Integer userId);
}
