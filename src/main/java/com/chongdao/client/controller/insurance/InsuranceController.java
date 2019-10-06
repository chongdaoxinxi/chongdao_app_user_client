package com.chongdao.client.controller.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.service.InsuranceFeeRecordService;
import com.chongdao.client.service.ShopChipService;
import com.chongdao.client.service.ShopService;
import com.chongdao.client.service.insurance.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private ShopChipService shopChipService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private InsuranceFeeRecordService insuranceFeeRecordService;

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
     * 获取指定医院的可用芯片数据
     * @param shopId
     * @param core
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getPetChipAppiontShop")
    public ResultResponse getPetChipAppiontShop(Integer shopId, String core, Integer pageNum, Integer pageSize) {
        return shopChipService.getShopChipAppointShop(shopId, core, 1, pageNum, pageSize);
    }

    /**
     * 获取医院类商家
     * @return
     */
    @PostMapping("getInsuranceShop")
    public ResultResponse getInsuranceShop(Double lng, Double lat, String areaCode, Integer pageNum, Integer pageSize) {
        return shopService.getInsranceShopLimit3KM(lng, lat, areaCode, pageNum, pageSize);
    }

    /**
     * 获取用户医疗费用消费记录
     * @param token
     * @param status
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getUserInsuranceFeeRecordList")
    public ResultResponse getUserInsuranceFeeRecordList(String token, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        return insuranceFeeRecordService.getUserFeeRecordList(token, status, startDate, endDate, pageNum, pageSize);
    };

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

    /**
     * 轮询校验订单是否已经支付完成
     * @param insuranceOrderId
     * @return
     */
    @PostMapping("pollingCheckOrderStatus")
    public ResultResponse pollingCheckOrderStatus(Integer insuranceOrderId) {
        return insuranceService.pollingCheckOrderStatus(insuranceOrderId);
    }

    /**
     * 申请理赔
     * @return
     */
    @PostMapping("applyInsuranceClaims")
    public ResultResponse applyInsuranceClaims() {
        return null;
    }

    /**
     * 确认理赔金额
     * @return
     */
    @PostMapping("confirmInsuranceClaimsMoney")
    public ResultResponse confirmInsuranceClaimsMoney() {
        return null;
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

    /**
     * 审核理赔(包括平台审核, 保险公司审核)
     * @return
     */
    @PostMapping("auditInsuranceClaims")
    public ResultResponse auditInsuranceClaims() {
        return null;
    }
}
