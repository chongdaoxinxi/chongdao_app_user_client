package com.chongdao.client.task;

import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.repository.InsuranceOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/11/7
 * @Version 1.0
 **/
@Component
@Slf4j
public class InsuranceOrderTask {
    @Autowired
    private InsuranceOrderRepository insuranceOrderRepository;

    @Async
    @Scheduled(cron="0 0 2 * * ?")
    public void updateOverdueInsuranceOrder() {
        List<InsuranceOrder> list = insuranceOrderRepository.findByStatus(1);//待支付的订单
        for (InsuranceOrder insuranceOrder : list) {
            Date applyTime = insuranceOrder.getApplyTime();
            Date now = new Date();
            Long l = (now.getTime() - applyTime.getTime())/ (1000*60*60*24);
            if(l > 24) {
                //如果投保下单后24小时后还未支付, 将订单关闭
                insuranceOrder.setStatus(-1);
                insuranceOrderRepository.save(insuranceOrder);
            }
        }
    }
}
