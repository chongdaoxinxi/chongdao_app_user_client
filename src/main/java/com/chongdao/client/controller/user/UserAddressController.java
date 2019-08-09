package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAddress;
import com.chongdao.client.repository.UserAddressRepository;
import com.chongdao.client.service.UserAddressService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户收货地址
 * @Author onlineS
 * @Date 2019/5/6
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/address/")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private UserAddressRepository userAddressRepository;

    /**
     * 保存收货地址
     * @param uAddr
     * @return
     */
    @GetMapping("saveUserAddress")
    public ResultResponse saveUserAddress(UserAddress uAddr) {
        return userAddressService.saveUserAddress(uAddr);
    }

    /**
     * 获取收货地址详情
     * @param uAddrId
     * @return
     */
    @GetMapping("getUserAddressInfo")
    public ResultResponse<UserAddress> getUserAddressInfoById(Integer uAddrId) {
        return userAddressService.getUserAddressById(uAddrId);
    }

    /**
     * 获取收货地址列表
     * @param token
     * @return
     */
    @GetMapping("getUserAddressList")
    public ResultResponse<Page<UserAddress>> getUserAddressList(String token, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return userAddressService.getUserAddressList(tokenVo.getUserId(), pageNum, pageSize);
    }


    @GetMapping("{id}")
    public ResultResponse getUserAddressById(@PathVariable Integer id, String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        UserAddress userAddress = userAddressRepository.findByIdAndUserId(id, tokenVo.getUserId());
        return ResultResponse.createBySuccess(userAddress);

    }
}
