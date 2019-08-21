package com.chongdao.client.controller.insurance;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 保险业务
 * @Author onlineS
 * @Date 2019/5/29
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/insurance/")
public class InsuranceController {
    /////////////////////app端下单///////////////////////////////
    //下单 保存保单
    @PostMapping("addInsurance")
    public ResultResponse addInsurance() {
        return null;
    }

    //获取我的保单数据
    @PostMapping("getMyInsuranceData")
    public ResultResponse getMyInsuranceData() {
        return null;
    }

    //下载电子保单
    @PostMapping("downloadElectronicInsurancePolicy")
    public ResultResponse downloadElectronicInsurancePolicy() {
        return null;
    }

    //获取保单明细信息
    @PostMapping("getInsuranceDetail")
    public ResultResponse getInsuranceDetail() {
        return null;
    }

    /////////////////////PC端审核///////////////////////////////
    //获取保单分页数据
    @PostMapping("getInsuranceDataList")
    public ResultResponse getInsuranceDataList() {
        return null;
    }

    //一级/二级审核保单
    @PostMapping("auditInsurance")
    public ResultResponse auditInsurance() {
        return null;
    }

    //一级/二级拒绝保单
    @PostMapping("refuseInsurance")
    public ResultResponse refuseInsurance() {
        return null;
    }
}
