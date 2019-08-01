package com.chongdao.client.controller.insurance;

import com.chongdao.client.common.ResultResponse;
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

    //医疗险发送数据给保险公司生成保单
    public ResultResponse generateInsure() {
        //生成接口所需要的数据
        return null;
    }

    //接受保险公司返回的保单数据

    //运输险发送数据给保险公司生成保单

    //接受保险公司返回的运输险保单数据

    //医疗险理赔-传输数据给保险公司
}
