package com.chongdao.client.service.iml;

import com.chongdao.client.entitys.OrderOperateLog;
import com.chongdao.client.repository.OrderOperateLogRepository;
import com.chongdao.client.service.OrderOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/11/7
 * @Version 1.0
 **/
@Service
public class OrderOperateLogServiceImpl implements OrderOperateLogService {
    @Autowired
    private OrderOperateLogRepository orderOperateLogRepository;

    @Override
    @Transactional
    public void addOrderOperateLogService(Integer orderId, String orderNo, String note, Integer old, Integer target) {
        OrderOperateLog log = new OrderOperateLog();
        log.setOrderId(orderId);
        log.setOrderNo(orderNo);
        log.setNote(note);
        log.setOldStatus(old);
        log.setTargetStatus(target);
        log.setCreateTime(new Date());
        orderOperateLogRepository.save(log);
    }
}
