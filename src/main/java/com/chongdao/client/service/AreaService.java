package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

public interface AreaService {
    ResultResponse getTopLevelAreaDataList(Integer level, Integer isOpen);
    ResultResponse getAreaDataByParentId(Integer level, Integer isOpen, Integer pid);
}
