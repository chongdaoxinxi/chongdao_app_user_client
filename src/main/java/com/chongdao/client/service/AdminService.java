package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface AdminService {
    ResultResponse adminLogin(String username, String password);

    ResultResponse getAdminInfo(Integer managementId, String role);
}
