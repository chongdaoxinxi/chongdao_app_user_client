package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodsType;

public interface GoodsTypeService {
    ResultResponse addGoodsType(GoodsType goodsType);

    ResultResponse getSelectGoodsTypeSpecialConfig(Integer moduleId, Integer categoryId, Integer goodsTypeId);
}
