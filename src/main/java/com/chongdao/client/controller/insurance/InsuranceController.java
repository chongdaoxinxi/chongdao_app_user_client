package com.chongdao.client.controller.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.service.insurance.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * @Description 保险业务
 * @Author onlineS
 * @Date 2019/5/29
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/insurance/")
public class InsuranceController {
    @Autowired
    private InsuranceService insuranceService;

    /////////////////////app端下单///////////////////////////////

    /**
     * 下单 保存保单
     *
     * @param insuranceOrder
     * @return
     * @throws IOException
     */
    @PostMapping("addInsurance")
    public ResultResponse addInsurance(@RequestBody InsuranceOrder insuranceOrder) throws IOException {
        return insuranceService.saveInsurance(insuranceOrder);
    }

    /**
     * 获取我的保单数据
     *
     * @param token
     * @param pageSize
     * @param pageNum
     * @return
     */
    @PostMapping("getMyInsuranceData")
    public ResultResponse getMyInsuranceData(String token, Integer pageSize, Integer pageNum) {
        return insuranceService.getMyInsuranceData(token, pageSize, pageNum);
    }

    /**
     * 获取保单明细信息
     *
     * @param insuranceId
     * @return
     */
    @PostMapping("getInsuranceDetail")
    public ResultResponse getInsuranceDetail(Integer insuranceId) {
        return insuranceService.getInsuranceDetail(insuranceId);
    }

    /**
     * 下载电子保单
     *
     * @param insuranceOrderId
     * @return
     */
    @PostMapping("downloadElectronicInsurancePolicy")
    public ResultResponse downloadElectronicInsurancePolicy(Integer insuranceOrderId) {
        return insuranceService.downloadElectronicInsurancePolicy(insuranceOrderId);
    }

    /////////////////////PC端审核///////////////////////////////

    /**
     * 获取保单分页数据
     *
     * @param token
     * @param userName
     * @param insuranceOrderNo
     * @param start
     * @param end
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getInsuranceDataList")
    public ResultResponse getInsuranceDataList(String token, Integer insuranceType, String userName, String phone, String insuranceOrderNo, Date start, Date end, Integer status, Integer pageNum, Integer pageSize) {
        return insuranceService.getInsuranceDataList(token, insuranceType, userName, phone, insuranceOrderNo, start, end, status, pageNum, pageSize);
    }

    /**
     * 一级/二级审核保单
     *
     * @param insuranceOrderId
     * @param targetStatus
     * @param note
     * @return
     */
    @PostMapping("auditInsurance")
    public ResultResponse auditInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note) {
        return insuranceService.auditInsurance(token, insuranceOrderId, targetStatus, note);
    }

    /**
     * 一级/二级拒绝保单
     *
     * @param insuranceOrderId
     * @param targetStatus
     * @param note
     * @return
     */
    @PostMapping("refuseInsurance")
    public ResultResponse refuseInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note) {
        return insuranceService.refuseInsurance(token, insuranceOrderId, targetStatus, note);
    }
}
