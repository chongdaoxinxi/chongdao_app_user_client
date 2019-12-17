package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface UserXcxService {

    /**
     * 校验是否小程序老用户
     * @param phone
     * @return
     */
    boolean checkIsXcxOldUser(String phone);

    /**
     * 发放配送优惠券至小程序老用户
     * @param phone
     * @return
     */
    ResultResponse addServiceCpnToXcxUser(String phone);
}
