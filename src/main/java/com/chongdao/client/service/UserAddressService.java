package com.chongdao.client.service;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAddress;
import org.springframework.data.domain.Page;

/**
 * 用户收货地址接口
 */
public interface UserAddressService {
    ResultResponse<UserAddress> getUserAddressById(Integer uAddrId);
    ResultResponse<Page<UserAddress>> getUserAddressList(Integer userId, Integer pageNum, Integer pageSize);
    ResultResponse saveUserAddress(UserAddress uAddr);
}
