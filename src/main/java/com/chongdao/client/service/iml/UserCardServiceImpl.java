package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.CardUserMapper;
import com.chongdao.client.service.UserCardService;
import com.chongdao.client.vo.CardUserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description 获取用户优惠券
 * @Author onlineS
 * @Date 2019/5/6
 * @Version 1.0
 **/
@Service
public class UserCardServiceImpl implements UserCardService {
    @Autowired
    private CardUserMapper cardUserMapper;

    @Override
    public ResultResponse<PageInfo<CardUserVo>> getUserCard(Integer userId, Integer type, Integer pageNum, Integer pageSize) {
        return Optional.ofNullable(userId)
                .map(id -> {
                    PageHelper.startPage(pageNum, pageSize);
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), new PageInfo<>(cardUserMapper.getCardUserVoByUserId(userId, type)));
                })
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }
}
