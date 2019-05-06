package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAddress;
import com.chongdao.client.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/6
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/address")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    /**
     * 保存收货地址
     * @param uAddr
     * @return
     */
    @GetMapping("/saveUserAddress")
    public ResultResponse saveUserAddress(UserAddress uAddr) {
        return userAddressService.saveUserAddress(uAddr);
    }

    /**
     * 获取收货地址
     * @param uAddrId
     * @return
     */
    @GetMapping("/getUserAddressInfo")
    public ResultResponse<UserAddress> getUserAddressInfoById(Integer uAddrId) {
        return userAddressService.getUserAddressById(uAddrId);
    }

    /**
     * 获取收货地址列表
     * @param userId
     * @return
     */
    @GetMapping("/getUserAddressList")
    public ResultResponse<List<UserAddress>> getUserAddressList(Integer userId) {
        return userAddressService.getUserAddressList(userId);
    }
}
