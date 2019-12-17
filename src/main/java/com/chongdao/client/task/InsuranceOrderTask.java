package com.chongdao.client.task;

import com.chongdao.client.entitys.InsuranceFeeRecord;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.entitys.InsuranceShopChip;
import com.chongdao.client.repository.InsuranceFeeRecordRepository;
import com.chongdao.client.repository.InsuranceOrderRepository;
import com.chongdao.client.repository.InsuranceShopChipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Description 超时保险订单删除定时任务
 * @Author onlineS
 * @Date 2019/11/7
 * @Version 1.0
 **/
@Component
@Slf4j
public class InsuranceOrderTask {
    @Autowired
    private InsuranceOrderRepository insuranceOrderRepository;
    @Autowired
    private InsuranceShopChipRepository insuranceShopChipRepository;
    @Autowired
    private InsuranceFeeRecordRepository insuranceFeeRecordRepository;

    /**
     * 保险订单超时未支付关闭
     */
    @Async
    @Scheduled(cron="0 0 2 * * ?")
    public void updateOverdueInsuranceOrder() {
        List<InsuranceOrder> list = insuranceOrderRepository.findByStatus(1);//待支付的订单
        for (InsuranceOrder insuranceOrder : list) {
            Date applyTime = insuranceOrder.getApplyTime();
            Date now = new Date();
            Long l = (now.getTime() - applyTime.getTime())/ (1000*60*60);
            if(l > 24) {
                //如果投保下单后24小时后还未支付, 将订单关闭
                insuranceOrder.setStatus(-1);
                insuranceOrderRepository.save(insuranceOrder);
                //如果选择了宠物芯片, 则恢复宠物芯片为可用状态
                Integer chipId = insuranceOrder.getMedicalInsuranceShopChipId();
                if(chipId != null) {
                    InsuranceShopChip insuranceShopChip = insuranceShopChipRepository.findById(chipId).orElse(null);
                    if(insuranceShopChip != null) {
                        insuranceShopChip.setStatus(1);
                        insuranceShopChip.setSelectedTime(null);
                        insuranceShopChip.setUsedTime(null);
                        insuranceShopChipRepository.save(insuranceShopChip);
                    }
                }
                System.out.println(insuranceOrder.getInsuranceOrderNo() + ", 超时未支付被关闭!");
            }
        }
        System.out.println("保险订单超时未支付关闭任务执行完毕" + "当前时间:" + new Date());
    }

    /**
     * 超时未支付医疗费用订单自动关闭
     */
    @Async
    @Scheduled(cron="0 0 3 * * ?")
    public void updateOverdueInsuranceFeeOrder() {
        List<InsuranceFeeRecord> list = insuranceFeeRecordRepository.findByStatus(-1);//待支付医疗费用
        for(InsuranceFeeRecord record : list) {
            Date createTime = record.getCreateTime();
            Date now = new Date();
            Long l = (now.getTime() - createTime.getTime())/ (1000*60*60);
            if(l > 24) {
                record.setStatus(-2);
                insuranceFeeRecordRepository.save(record);
                System.out.println("医疗费用订单" + record.getOrderNo() + ", 超时未支付被关闭!");
            }
        }
        System.out.println("医疗费用订单超时未支付关闭任务执行完毕" + "当前时间:" + new Date());
    }

    /**
     * 更新保险订单状态(由等待期进入保障期)
     */
    @Async
    @Scheduled(cron="0 0 4 * * ?")
    public void updateInsuranceOrderStatus() {
        List<InsuranceOrder> list = insuranceOrderRepository.findByStatus(2);//等待期的保单
//        List<InsuranceFeeRecord> list = insuranceFeeRecordRepository.findByStatus(2);
        for(InsuranceOrder order : list) {
            Date createTime = order.getCreateTime();
            Date now = new Date();
            Long l = (now.getTime() - createTime.getTime())/ (1000*60*60*24);
            if(l >= 7) {
                order.setStatus(3);
                insuranceOrderRepository.save(order);
                System.out.println("保险订单" + order.getOrderNo() + ", 等待期已满, 进入保障期!");
            }
        }
        System.out.println("保险订单等待期满转保障期任务执行完毕" + "当前时间:" + new Date());
    }
}
