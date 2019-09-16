package com.chongdao.client.controller.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.repository.InsuranceOrderRepository;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Description 保险业务对外接口
 * @Author onlineS
 * @Date 2019/5/29
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/insurance_external/")
public class InsuranceExternalController {
    @Autowired
    private InsuranceExternalService insuranceExternalService;
    @Autowired
    private InsuranceOrderRepository insuranceOrderRepository;

    //保险发送数据给保险公司生成保单
    @PostMapping("generateInsure")
    public ResultResponse generateInsure(Integer insuranceOrderId)  throws IOException {
        //生成接口所需要的数据
        InsuranceOrder insuranceOrder = insuranceOrderRepository.findById(insuranceOrderId).orElse(null);
        if(insuranceOrder == null) {
            return ResultResponse.createByErrorMessage("无效的保险订单ID!");
        }
        return insuranceExternalService.generateInsure(insuranceOrder);
    }

    /**
     * 投保支付回调
     * @param payCallBackInfo
     * @return
     * @throws IOException
     */
    @PostMapping("payInsureCallBack")
    public ResultResponse payInsureCallBack(@RequestBody String payCallBackInfo) throws IOException {
        //TODO
        System.out.println("payCallBackInfo:" + payCallBackInfo);
//        System.out.println("payCallBackInfo.code:" + payCallBackInfo.get("code"));
//        System.out.println("payCallBackInfo.policyUrl:" + payCallBackInfo.get("policyUrl"));
//        System.out.println("payCallBackInfo.downloadUrl:" + payCallBackInfo.get("downloadUrl"));
        System.out.println("回调成功!");
        return insuranceExternalService.payCallBackManage(payCallBackInfo);
    }

    /**
     * 请求电子发票
     * @return
     */
    @PostMapping("requestInvoiceInfo")
    public ResultResponse requestInvoiceInfo() {
        return insuranceExternalService.requestInvoiceInfo();
    }
}
