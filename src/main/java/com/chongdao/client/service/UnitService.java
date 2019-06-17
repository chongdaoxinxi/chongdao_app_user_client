package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Unit;

import java.util.List;

public interface UnitService {
    ResultResponse<List<Unit>> getUnitList(Integer moduleId, Integer categoryId);
}
