package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CardUserVo;
import com.github.pagehelper.PageInfo;

public interface UserCardService {
    ResultResponse<PageInfo<CardUserVo>> getUserCard(Integer userId, Integer type, Integer pageNum, Integer pageSize);
}
