package com.chongdao.client.controller.order;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 预下单/提交订单
     *  orderType 1代表预下单 2代表下单
     *  serviceType 服务类型 1.双程 2.单程 3.到店自取
     * @return
     */
    @PostMapping("preOrCreateOrder")
    public ResultResponse<OrderVo> preOrCreateOrder(String token, OrderCommonVO orderCommonVO){
        //校验用户是否登录
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.preOrCreateOrder(tokenVo.getUserId(),orderCommonVO);
    }


    /**
     * 根据type获取相应的订单
     * @param type 1:已支付未接单,2:已接单,3:服务中,4.已完成
     * @return
     */
    @GetMapping("orderTypeList")
    public ResultResponse<PageInfo> getOrderTypeList(@RequestParam("type") String type,
                                                     @RequestParam("token") String token,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.getOrderTypeList(tokenVo.getUserId(), type,pageNum,pageSize);
    }


}
