package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.CardUserVo;

import java.util.List;

public interface UserCardService {
    ResultResponse<List<CardUserVo>> getUserCard(Integer userId, Integer type);
}
