package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceFeeRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface InsuranceFeeRecordService {
    /**
     * 获取保险医疗费用记录
     * @param token
     * @param userName
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse getInsuranceFeeRecordData(String token, String userName,String shopName, Date startDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 添加保险医疗费用记录
     * @param insuranceFeeRecord
     * @return
     */
    ResultResponse addInsuranceFeeRecord(InsuranceFeeRecord insuranceFeeRecord);

    /**
     * 保险医疗费用支付
     * @param req
     * @param insuranceFeeRecordNo
     * @param totalFee
     * @param goodStr
     * @param openId
     * @param payType
     * @return
     */
    ResultResponse payInsuranceFee(HttpServletRequest req, String insuranceFeeRecordNo, Integer totalFee, String goodStr, String openId, Integer payType);
}
