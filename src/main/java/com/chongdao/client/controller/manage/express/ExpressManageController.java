package com.chongdao.client.controller.manage.express;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/15
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/express_manage/")
public class ExpressManageController {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResultResponse login(String username, String password) {
        return null;
    }

    /**
     * 登出
     * @param token
     * @return
     */
    @GetMapping("/logout")
    public ResultResponse logout(String token) {
        return null;
    }

    /**
     * 获取订单列表(可接, 已接, 已完成
     * @param token
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getExpressOrderList")
    public ResultResponse getExpressOrderList(String token, Integer type, Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 获取配送管理员订单列表(商家已接单, 商家未接单)
     * @param token
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getExpressManageOrderList")
    public ResultResponse getExpressManageOrderList(String token, Integer type, Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 接单
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("/expressAcceptOrder")
    public ResultResponse expressAcceptOrder(String token, Integer orderId){
        return null;
    }

    /**
     * 取消订单(状态变为-1)-----------疑问 此方法存在合理嘛
     * @param orderId
     * @return
     */
    @GetMapping("/cancelOrder")
    public ResultResponse cancelOrder(String token, Integer orderId) {
        return null;
    }

    /**
     * 到店
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("/arriveShop")
    public ResultResponse arriveShop(String token, Integer orderId) {
        return null;
    }

    /**
     * 服务完成
     * @param token
     * @param orderId
     * @return
     */
    @GetMapping("/serviceComplete")
    public ResultResponse serviceComplete(String token, Integer orderId) {
        return null;
    }
}
