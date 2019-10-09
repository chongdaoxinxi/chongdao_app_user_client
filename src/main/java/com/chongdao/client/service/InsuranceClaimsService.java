package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceClaims;

import java.util.Date;

public interface InsuranceClaimsService {
    /**
     * 添加/保存理赔
     * @param insuranceClaims
     * @return
     */
    ResultResponse saveInsuranceClaims(InsuranceClaims insuranceClaims);

    /**
     * 删除理赔
     * @param claimsId
     * @return
     */
    ResultResponse removeInsuranceClaims(Integer claimsId);

    /**
     * 确认理赔金额
     * @param claimsId
     * @return
     */
    ResultResponse confirmInsuranceClaimsMoney(Integer claimsId);

    /**
     * 获取我的理赔记录列表
     * @param token
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse getMyClaimsList(String token, Date startDate, Date endDate, Integer pageNum, Integer pageSize);

    ResultResponse getAppiontInsuranceOrderClaims(Integer insuranceOrderId);
}
