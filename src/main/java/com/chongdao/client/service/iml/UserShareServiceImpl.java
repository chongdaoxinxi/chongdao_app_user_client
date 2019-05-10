package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserShare;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.UserShareRepository;
import com.chongdao.client.service.UserShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Description 用户分享
 * @Author onlineS
 * @Date 2019/5/10
 * @Version 1.0
 **/
@Service
public class UserShareServiceImpl implements UserShareService {
    @Autowired
    private UserShareRepository userShareRepository;

    /**
     * 根据用户id查询分享记录
     * @param userId
     * @return
     */
    @Override
    public UserShare getUserShareByUserId(Integer userId) {
        return Optional.ofNullable(userId).map(id -> Optional.ofNullable(userShareRepository.findByUserId(userId)).orElse(null)).orElse(null);
    }

    /**
     * 保存/更新用户分享
     * @param us
     * @return
     */
    @Override
    public UserShare saveUserShare(UserShare us) {
        return Optional.ofNullable(us).map(usI -> userShareRepository.saveAndFlush(us)).orElse(null);
    }

    @Override
    public ResultResponse userShare(Integer userId, Integer type) {
        if(userId != null) {
            UserShare us = userShareRepository.findByUserId(userId);
            Map<String, Object> resp = new HashMap<>();
            if(us != null) {
                Integer maxMun = us.getMaxMun();
                us.setMaxMun(maxMun++);
                us.setUpdateTime(new Date());
                resp.put("is_full", true);
            } else {
                us = new UserShare();
                us.setUserId(userId);
                us.setMaxMun(1);
                // 这里的逻辑是什么
//                us.setCardId();
//                us.setCouponId();
                us.setCreateTime(new Date());
                resp.put("is_full", false);
            }
                resp.put("times", us.getMaxMun());
            userShareRepository.saveAndFlush(us);
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), resp);
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }
}
