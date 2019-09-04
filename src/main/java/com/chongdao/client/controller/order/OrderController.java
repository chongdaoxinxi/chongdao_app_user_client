package com.chongdao.client.controller.order;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.OrderEval;
import com.chongdao.client.entitys.OrderExpressEval;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.*;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/order/")
@Slf4j
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 预下单/提交订单
     *  orderType 1代表预下单 2代表下单 3 拼单
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
     * 追加订单
     * @param
     * @param token
     * @param shopId
     * @param orderType (4)
     * @return
     */
    @PostMapping("reAddOrder")
    public ResultResponse<OrderVo> reAddOrder(@RequestParam String token, @RequestParam String orderNo,
                                              @RequestParam Integer shopId, @RequestParam Integer orderType,
                                              @RequestParam BigDecimal totalPrice){
        //校验用户是否登录
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.reAddOrder(tokenVo.getUserId(),orderNo,shopId,orderType,totalPrice);
    }


    /**
     * 再来一单
     * @param orderNo
     * @param token
     * @return
     */
    @GetMapping("anotherOrder/{shopId}")
    public ResultResponse anotherOrder(@PathVariable Integer shopId,String orderNo,String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.anotherOrder(tokenVo.getUserId(),orderNo,shopId);
    }


    /**
     * 根据type获取相应的订单
     * @param type 1:已支付未接单,2:服务中,3.已完成
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


    /**
     * 订单详情
     * @param token
     * @param orderNo
     * @return
     */
    @GetMapping("orderDetail")
    public ResultResponse orderDetail(@RequestParam("token") String token,
                                      @RequestParam("orderNo") String orderNo){

        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.orderDetail(tokenVo.getUserId(), orderNo);
    }

    /**
     * 评价晒单 初始化
     * @param orderNo
     * @param token
     * @return
     */
    @GetMapping("initOrderEval")
    public ResultResponse initOrderEval(String orderNo,String token){
        LoginUserUtil.resultTokenVo(token);
        return orderService.initOrderEval(orderNo);
    }

    /**
     * 订单评价
     * @param orderEval
     * @return
     */
    @PostMapping("orderEval")
    public ResultResponse evalOrder(@Valid OrderEval orderEval, @Valid OrderExpressEval orderExpressEval,
                                    BindingResult bindingResult){
        LoginUserUtil.resultTokenVo(orderEval.getToken());
        if (bindingResult.hasErrors()){
            log.error("【订单评价】参数不正确，orderEval={}:",orderEval);
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),bindingResult.getFieldError().getDefaultMessage());
        }
        return orderService.orderEval(orderEval,orderExpressEval);
    }


    /**
     * 用户申请退款
     * @param orderNo
     * @param token
     * @return
     */
    @PostMapping("refund")
    public ResultResponse refund(String orderNo,String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.refund(tokenVo.getUserId(), orderNo);
    }




}
