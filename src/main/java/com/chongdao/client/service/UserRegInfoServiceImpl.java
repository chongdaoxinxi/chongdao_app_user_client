package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserRegInfo;
import com.chongdao.client.repository.UserRegInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/12/11
 * @Version 1.0
 **/
@Service
public class UserRegInfoServiceImpl implements UserRegInfoService {
    @Autowired
    private UserRegInfoRepository userRegInfoRepository;

    @Override
    public ResultResponse addUserRegInfo(Integer userId, String regId, String alias, String userAccount) {
        if(userId == null) {
            return ResultResponse.createByErrorMessage("userId不能为空!");
        }
        UserRegInfo info = userRegInfoRepository.findByUserId(userId);
        if(info == null) {
            UserRegInfo userRegInfo = new UserRegInfo();
            userRegInfo.setUserId(userId);
            userRegInfo.setRegId(regId);
            userRegInfo.setAlias(alias);
            userRegInfo.setUserAccount(userAccount);
            userRegInfo.setCreateTime(new Date());
            userRegInfoRepository.save(userRegInfo);
            return ResultResponse.createBySuccess("保存用户标识成功!");
        } else {
            info.setRegId(regId);
            info.setAlias(alias);
            info.setUserAccount(userAccount);
            info.setUpdateTime(new Date());
            userRegInfoRepository.save(info);
            return ResultResponse.createBySuccess("更新用户标识成功!");
        }
    }
}
