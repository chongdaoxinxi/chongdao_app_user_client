package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodsType;

public interface GoodsTypeService {
    ResultResponse addGoodsType(GoodsType goodsType);

    ResultResponse getSelectGoodsTypeSpecialConfig(Integer moduleId, Integer categoryId, Integer goodsTypeId);

    /**
     * 二级类别
     * @param categoryId
     * @return
     */
    ResultResponse getGoodsTypeListByCategoryId(Integer categoryId);

    ResultResponse getSecondLevelTypeByChild(Integer childId);

    /**
     * 三级类别
     * @param parentId
     * @return
     */
    ResultResponse getGoodsTypeListByParentId(Integer parentId);
}
