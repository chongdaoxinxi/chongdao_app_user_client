package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodTaste;

public interface GoodTasteService {
    //保存口味新数据
    ResultResponse  saveGoodTaste(GoodTaste goodTaste);

    //获取口味数据列表
    ResultResponse getGoodTasteList();
}
