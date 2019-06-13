package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderInfo;
import com.chongdao.client.entitys.ShopBill;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.ShopBillRepository;
import com.chongdao.client.service.ShopBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/13
 * @Version 1.0
 **/
@Service
public class ShopBillServiceImpl implements ShopBillService {
    @Autowired
    private ShopBillRepository shopBillRepository;

    @Override
    public ResultResponse addShopBillRecord(OrderInfo orderInfo, Integer type, String note,  BigDecimal realMoney) {
        ShopBill sb = new ShopBill();
        sb.setShopId(orderInfo.getShopId());
        sb.setUserId(orderInfo.getUserId());
        sb.setPrice(realMoney);
        sb.setType(type);
        sb.setNote(note);
        sb.setCreatedate(new Date());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopBillRepository.saveAndFlush(sb));
    }
}
