package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/12/12
 * @Version 1.0
 **/
public interface ServiceRuleService {
    //获取配送规则参数
    ResultResponse getServiceRuleInfo(BigDecimal originServicePrice, Integer serviceType, Integer isService, String areaCode, BigDecimal serviceDistance);
}
