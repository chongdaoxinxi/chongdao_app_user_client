package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 商家端订单管理
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/shop_order_manage/")
public class ShopOrderManageController {
    @Autowired
    private OrderService orderService;

    /**
     * 获取订单列表
     * @param token
     * @param type 1:待接单;2:已接单;3:已完成;4:退款中;all:全部
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getOrderList")
    public ResultResponse getOrderList(String token, String type, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.getShopOrderTypeList(tokenVo.getUserId(), type, pageNum, pageSize);
    }

    /**
     * 接单
     * @param orderId
     * @return
     */
    @GetMapping("acceptOrder")
    public ResultResponse acceptOrder(Integer orderId) {
        return orderService.shopAcceptOrder(orderId);
    }

    /**
     * 拒单
     * @param orderId
     * @return
     */
    @GetMapping("refuseOrder")
    public ResultResponse refuseOrder(Integer orderId) {
        return orderService.shopRefuseOrder(orderId);
    }

    /**
     * 退款
     * @param orderId
     * @return
     */
    @GetMapping("refundOrder")
    public ResultResponse refundOrder(Integer orderId) {
        return orderService.shopRefundOrder(orderId);
    }

    /**
     * 服务完成
     * @param orderId
     * @return
     */
    @GetMapping("shopServiceCompleted")
    public ResultResponse shopServiceCompleted(Integer orderId) { return orderService.shopServiceCompleted(orderId);}
}
