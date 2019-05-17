package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

/**
 * 配送端
 */
public interface ExpressManageService {

    ResultResponse expressLogin(String username, String password);
    ResultResponse expressLogout(String username, String password);
}
