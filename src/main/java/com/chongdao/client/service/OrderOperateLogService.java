package com.chongdao.client.service;

import org.springframework.stereotype.Service;

public interface OrderOperateLogService {

    /**
     * 添加订单流转操作日志
     * @param orderId
     * @param orderNo
     * @param note
     * @param old
     * @param target
     */
    void addOrderOperateLogService(Integer orderId, String orderNo, String note, Integer old, Integer target);
}
