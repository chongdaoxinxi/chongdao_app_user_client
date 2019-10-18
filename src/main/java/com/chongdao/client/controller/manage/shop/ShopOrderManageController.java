package com.chongdao.client.controller.manage.shop;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.service.ShopApplyService;
import com.chongdao.client.service.ShopBillService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

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
    @Autowired
    private ShopApplyService shopApplyService;
    @Autowired
    private ShopBillService shopBillService;

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
     * 商家查看订单详情
     * @param token
     * @param orderNo
     * @return
     */
    @GetMapping("getShopOrderDetail")
    public ResultResponse getShopOrderDetail(@RequestParam("token") String token,
                                             @RequestParam("orderNo") String orderNo) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.getShopOrderDetail(tokenVo.getUserId(), orderNo);
    }

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    @PostMapping("getShopOrderDetail")
    public ResultResponse getOrderDetailShopPc(Integer orderId) {
        return ResultResponse.createBySuccess(orderService.getOrderDetailByOrderId(orderId));
    }

    /**
     * 根据流水id获取订单详情
     * @param shopBillId
     * @return
     */
    @PostMapping("getShopBillDetail")
    public ResultResponse getShopBillDetail(Integer shopBillId) {
        return shopBillService.getShopBillOrderDetailById(shopBillId);
    }

    /**
     * 获取订单列表Pc
     * @param token
     * @param orderNo
     * @param username
     * @param phone
     * @param orderStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("getOrderListPc")
    public ResultResponse getOrderListPc(String token, String orderNo, String username, String phone, String orderStatus, Date startDate, Date endDate, Integer pageNum, Integer pageSize) throws Exception {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.getShopOrderTypeListPc(tokenVo.getRole(), tokenVo.getUserId(), orderNo, username, phone, orderStatus, startDate, endDate, pageNum, pageSize);
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

    /**
     * 申请提现
     * @param token
     * @return
     */
    @GetMapping("applyWithdrawal")
    public ResultResponse applyWithdrawal(String token, BigDecimal money, String applyNote){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return shopApplyService.addShopApplyRecord(tokenVo.getUserId(), money, applyNote);}
}
