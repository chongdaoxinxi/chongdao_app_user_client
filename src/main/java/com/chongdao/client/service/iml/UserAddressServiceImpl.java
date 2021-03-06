package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAddress;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.UserAddressRepository;
import com.chongdao.client.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
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
    public ResultResponse<Page<UserAddress>> getUserAddressList(Integer userId, Integer pageNum, Integer pageSize) {
        if(userId != null && pageNum != null && pageSize != null) {
            Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "createTime");
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAddressRepository.findByUserIdAndStatus(userId, 1, pageable));
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse saveUserAddress(UserAddress uAddr) {
        return Optional.ofNullable(uAddr).map(u -> {
            if(Optional.ofNullable(u.getId()).isPresent()) {
                u.setUpdateTime(new Date());
            } else {
                u.setCreateTime(new Date());
            }
            //如果新增/编辑的是默认地址的话, 将其他默认地址变为非默认地址
            if(u.getIsDefaultAddress() == 1) {
                disableDefaultAddress();
            }
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAddressRepository.saveAndFlush(u));
        }).orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 将现有的默认地址修改为非默认地址
     */
    private void disableDefaultAddress() {
        Iterable<UserAddress> it = userAddressRepository.findByIsDefaultAddress(1);
        Iterator<UserAddress> iterator = it.iterator();
        while(iterator.hasNext()) {
            UserAddress next = iterator.next();
            next.setIsDefaultAddress(-1);
        }
        userAddressRepository.saveAll(it);
    }
}
