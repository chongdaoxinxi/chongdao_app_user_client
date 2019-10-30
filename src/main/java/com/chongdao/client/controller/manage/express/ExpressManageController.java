package com.chongdao.client.controller.manage.express;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.ExpressManageService;
import com.chongdao.client.service.ExpressOrderService;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Description 配送员端接口
 * @Author onlineS
 * @Date 2019/5/15
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/express_manage/")
public class ExpressManageController {
    @Autowired
    private ExpressManageService expressManageService;
    @Autowired
    private ExpressOrderService expressOrderService;
    @Autowired
    private OrderService orderService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @GetMapping("login")
    public ResultResponse login(String username, String password) {
        return expressManageService.expressLogin(username, password);
    }

    /**
     * 登出
     * @param token
     * @return
     */
    @GetMapping("logout")
    public ResultResponse logout(String token) {
        return null;
    }

    /**
     * 获取订单列表(可接, 已接, 已完成)
     * @param token
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getExpressOrderList")
    public ResultResponse<PageInfo> getExpressOrderList(String token, String type, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.expressOrderList(tokenVo.getUserId(), type, pageNum, pageSize);
    }

    /**
     * 获取配送管理员订单列表(商家已接单, 商家未接单)
     * @param token
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getExpressManageOrderList")
    public ResultResponse<PageInfo> getExpressManageOrderList(String token, String type, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.expressAdminOrderList(tokenVo.getUserId(), type, pageNum, pageSize);
    }

    /**
     *获取商家已接单分布统计
     * @param token
     * @param type 1: 上门接宠, 2: 服务中, 3:到店接宠, 4: 已完成
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("getShopAcceptedOrderStatics")
    public ResultResponse<PageInfo> getShopAcceptedOrderStatics(String token, String type, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return orderService.getShopAcceptedOrderStatics(tokenVo.getUserId(), type, pageNum, pageSize);
    }

    /**
     * 接单
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("expressAcceptOrder")
    public ResultResponse expressAcceptOrder(String token, Integer orderId){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return expressOrderService.expressAcceptOrder(tokenVo.getUserId(), orderId);
    }

    /**
     * 取消订单(状态变为-1)-----------疑问 此方法存在合理嘛
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("cancelOrder")
    public ResultResponse cancelOrder(String token, Integer orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return expressOrderService.cancelOrder(tokenVo.getUserId(), orderId);
    }

    /**
     * 到店
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("arriveShop")
    public ResultResponse arriveShop(String token, Integer orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return expressOrderService.arriveShop(tokenVo.getUserId(), orderId);
    }

    /**
     * 服务完成
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("serviceComplete")
    public ResultResponse serviceComplete(String token, Integer orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return expressOrderService.serviceComplete(tokenVo.getUserId(), orderId);
    }

    /**
     * 单程(店->家), 配送员到店后开始配送时的短信通知
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("expressStartServiceInSingleTripNotice")
    public ResultResponse expressStartServiceInSingleTripNotice(String token, Integer orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return expressOrderService.expressStartServiceInSingleTripNotice(tokenVo.getUserId(), orderId);
    }

    /**
     * 接到宠物(不管是店->家, 还是家->店, 亦或是双程)
     * @param token
     * @param orderId
     * @return
     */
    @PostMapping("received")
    public ResultResponse received(String token, Integer orderId) throws IOException {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return expressOrderService.received(tokenVo.getUserId(), orderId);
    }

    /**
     * 送达宠物(不管是店->家, 还是家->店, 亦或是双程)
     * @param token
     * @param orderId
     * @return
     */
    @PostMapping("delivery")
    public ResultResponse delivery(String token, Integer orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return expressOrderService.delivery(tokenVo.getUserId(), orderId);
    }

    /**
     * 获取已完成订单统计
     * @param token
     * @return
     */
    @PostMapping("getCompleteOrderStatics")
    public ResultResponse getCompleteOrderStatics(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return expressOrderService.getCompleteOrderStatics(tokenVo.getUserId());
    }
}
