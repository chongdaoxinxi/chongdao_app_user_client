package com.chongdao.client.service;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAddress;

import java.util.List;

/**
 * 用户收货地址接口
 */
public interface UserAddressService {
    ResultResponse<UserAddress> getUserAddressById(Integer uAddrId);
    ResultResponse<List<UserAddress>> getUserAddressList(Integer userId);
    ResultResponse saveUserAddress(UserAddress uAddr);
}
