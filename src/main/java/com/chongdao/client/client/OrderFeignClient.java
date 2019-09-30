package com.chongdao.client.client;

import com.chongdao.client.client.dto.*;
import com.chongdao.client.entitys.Express;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient(name = "WX-SERVICE",configuration = FeignConfig.class, fallback = OrderFeignClientFallBack.class)
public interface OrderFeignClient {

    /**
     * 获取所有待接单的数据
     * @param shopId
     * @param orderStatus
     * @return
     */
    @GetMapping("/o/list")
    List<OrderInfo> list(@RequestParam("shopId") Integer shopId, @RequestParam("orderStatus") String orderStatus);

    /**
     * 根据订单id获取详情
     * @param orderId
     * @return
     */
    @GetMapping("/o/detail")
    List<OrderDetail> detail(@RequestParam Integer orderId);

    /**
     * 根据订单号获取订单信息
     * @param orderNo
     * @return
     */
    @GetMapping("/o/getOrderInfoByOrderNo")
    OrderInfo getOrderInfoByOrderNo(@RequestParam String orderNo);


    @GetMapping("/o/getExpressById")
    Express getExpressById(@RequestParam Integer expressId);

    @GetMapping("/o/getShop")
    Shop getShop(@RequestParam Integer shopId);

    @GetMapping("/o/getUser")
    User getUser(@RequestParam Integer userId);

    @GetMapping("/o/getAddress")
    List<OrderAddress> getAddress(@RequestParam Integer orderId);


    @PostMapping
    void updateOrderStatus(@RequestParam String orderNo,@RequestParam Integer status);
}
