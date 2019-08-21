package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import com.chongdao.client.service.insurance.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/2
 * @Version 1.0
 **/
@Service
public class InsuranceServiceImpl implements InsuranceService {
    @Autowired
    private InsuranceExternalService insuranceExternalService;

    /**
     * 保存保单数据
     * @return
     */
    @Override
    public ResultResponse saveMedicalIusurance(InsuranceOrder insuranceOrder) {
        //先将数据保存在我们数据库

        //请求外部接口, 生成保单
        insuranceExternalService.generateInsure(insuranceOrder);
        return null;
    }
}
