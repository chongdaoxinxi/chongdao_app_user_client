package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Express;

public interface ExpressService {
    ResultResponse getExpressList(String token, String expressName, Integer selectType, Integer selectStatus, Integer pageNum, Integer pageSize);

    ResultResponse saveExpress(Express express);

    ResultResponse removeExpress(Integer expressId);
}
