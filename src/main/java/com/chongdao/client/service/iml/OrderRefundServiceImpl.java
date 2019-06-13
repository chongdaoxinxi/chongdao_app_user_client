package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.OrderRefund;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.OrderRefundRepository;
import com.chongdao.client.service.OrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/13
 * @Version 1.0
 **/
@Service
public class OrderRefundServiceImpl implements OrderRefundService {
    @Autowired
    private OrderRefundRepository orderRefundRepository;

    @Override
    public ResultResponse addOrderRefundRecord(OrderInfo orderInfo, Integer type, String note) {
        OrderRefund or = new OrderRefund();
        or.setOrderId(orderInfo.getId());
        or.setType(type);
        or.setNote(note);
        or.setCreatedate(new Date());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), orderRefundRepository.saveAndFlush(or));
    }
}
