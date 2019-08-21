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

    //保险发送数据给保险公司生成保单
    @PostMapping("generateInsure")
    public ResultResponse generateInsure() {
        //生成接口所需要的数据
        return insuranceExternalService.generateInsure(null);
    }
}
