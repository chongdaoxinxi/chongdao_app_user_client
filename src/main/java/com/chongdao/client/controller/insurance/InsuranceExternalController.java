package com.chongdao.client.controller.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //医疗险发送数据给保险公司生成保单
    @PostMapping("generateInsure")
    public ResultResponse generateInsure() {
        //生成接口所需要的数据
        return insuranceExternalService.generateInsure();
    }

    //接受保险公司返回的保单数据

    //运输险发送数据给保险公司生成保单

    //接受保险公司返回的运输险保单数据

    //医疗险理赔-传输数据给保险公司
}
