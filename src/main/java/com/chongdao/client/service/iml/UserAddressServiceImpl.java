package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAddress;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.UserAddressRepository;
import com.chongdao.client.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpNE;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Description 收货地址
 * @Author onlineS
 * @Date 2019/4/30
 * @Version 1.0
 **/
@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressRepository userAddressRepository;

    @Override
    public ResultResponse<UserAddress> getUserAddressById(Integer uAddrId) {
        return Optional.ofNullable(uAddrId)
                .map(id -> ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAddressRepository.findById(id).orElse(null)))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    @Override
    public ResultResponse<List<UserAddress>> getUserAddressList(Integer userId) {
        return Optional.ofNullable(userId)
                .map(id -> ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAddressRepository.findByUserId(id).orElse(null)))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    @Override
    public ResultResponse saveUserAddress(UserAddress uAddr) {
        return Optional.ofNullable(uAddr).map(u -> {
            if(Optional.ofNullable(u.getId()).isPresent()) {
                u.setUpdateTime(new Date());
            } else {
                u.setCreateTime(new Date());
            }
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAddressRepository.saveAndFlush(u));
        }).orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }
}
