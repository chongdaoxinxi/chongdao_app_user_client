package com.chongdao.client.controller.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.service.insurance.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    //下单 保存保单
    @PostMapping("addInsurance")
    public ResultResponse addInsurance(InsuranceOrder insuranceOrder) {
        return insuranceService.saveInsurance(insuranceOrder);
    }

    //获取我的保单数据
    @PostMapping("getMyInsuranceData")
    public ResultResponse getMyInsuranceData(String token, Integer pageSize, Integer pageNum) {
        return insuranceService.getMyInsuranceData(token, pageSize, pageNum);
    }

    //获取保单明细信息
    @PostMapping("getInsuranceDetail")
    public ResultResponse getInsuranceDetail(Integer insuranceId) {
        return insuranceService.getInsuranceDetail(insuranceId);
    }

    //下载电子保单
    @PostMapping("downloadElectronicInsurancePolicy")
    public ResultResponse downloadElectronicInsurancePolicy() {
        return null;
    }

    /////////////////////PC端审核///////////////////////////////
    //获取保单分页数据
    @PostMapping("getInsuranceDataList")
    public ResultResponse getInsuranceDataList(String token, String userName, String insuranceOrderNo, Date start, Date end, Integer pageNum, Integer pageSize) {
        return insuranceService.getInsuranceDataList(token, userName, insuranceOrderNo, start, end, pageNum, pageSize);
    }

    //一级/二级审核保单
    @PostMapping("auditInsurance")
    public ResultResponse auditInsurance(Integer insuranceOrderId, Integer targetStatus, String note) {
        return insuranceService.auditInsurance(insuranceOrderId, targetStatus, note);
    }

    //一级/二级拒绝保单
    @PostMapping("refuseInsurance")
    public ResultResponse refuseInsurance(Integer insuranceOrderId, Integer targetStatus, String note) {
        return insuranceService.refuseInsurance(insuranceOrderId, targetStatus, note);
    }
}
