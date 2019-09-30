package com.chongdao.client.client;

import com.chongdao.client.client.dto.OrderDetail;
import com.chongdao.client.client.dto.OrderInfo;
import com.chongdao.client.client.dto.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fenglong
 * @date 2019-09-25 16:07
 */
@RestController
@RequestMapping("/api/c/")
public class OrderFeignController {

    @Autowired
    private OrderFeignClient orderFeignClient;


    /**
     * 订单列表
     * @param shopId
     * @param orderStatus
     * @return
     */
    @GetMapping("list")
    public List<OrderInfo> list(@RequestParam Integer shopId, @RequestParam String orderStatus){
        List<OrderInfo> list = orderFeignClient.list(shopId, orderStatus);
        return list;
    }

    @GetMapping("getWxOrderDetail")
    public List<OrderDetail> getWxOrderDetail(@RequestParam Integer orderId){
        return orderFeignClient.detail(orderId);
    }


    @GetMapping("getOrderInfoByOrderNo")
    public OrderInfo getOrderInfoByOrderNo(@RequestParam String orderNo) throws Exception {
        return orderFeignClient.getOrderInfoByOrderNo(orderNo);
    }

    @GetMapping("getShop")
    public Shop getShop(@RequestParam Integer shopId) {
        Shop shop = orderFeignClient.getShop(shopId);
        return shop;
    }
}
