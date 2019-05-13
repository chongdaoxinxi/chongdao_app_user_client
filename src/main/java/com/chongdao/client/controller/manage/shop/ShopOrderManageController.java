package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shopOrderManage/")
public class ShopOrderManageController {
    /**
     * 获取订单列表
     * @param shopId
     * @param type 1:待接单;2:已接单;3:已完成;4:退款中;null:全部
     * @return
     */
    public ResultResponse getOrderList(Integer shopId, Integer type) {
        return null;
    }

    /**
     * 拒单
     * @param orderId
     * @return
     */
    public ResultResponse refundOrder(Integer orderId) {
        return null;
    }

    /**
     * 接单
     * @param orderId
     * @return
     */
    public ResultResponse acceptOrder(Integer orderId) {
        return null;
    }
}
