package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.OrderCommonVO;
import com.chongdao.client.vo.OrderEvalVO;
import com.chongdao.client.vo.OrderShopVO;
import com.chongdao.client.vo.OrderVo;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.Date;

;

public interface OrderService {
    ResultResponse preOrCreateOrder(Integer userId, OrderCommonVO orderCommonVO);

    /**
     * 再来一单
     * @param userId
     * @param orderNo
     * @return
     */
    ResultResponse anotherOrder(Integer userId, String orderNo,Integer shopId);

    /**
     * 根据type 获取订单列表
     * @param userId
     * @param type
     * @return
     */
    ResultResponse<PageInfo> getOrderTypeList(Integer userId, String type, int pageNum, int pageSize);

    /**
     * 订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    ResultResponse orderDetail(Integer userId, String orderNo);

    OrderShopVO getOrderDetailByOrderId(Integer orderId);

    // ResultResponse<OrderVo> createOrder(OrderVo orderVo,OrderCommonVO orderCommonVO);

    ResultResponse<PageInfo> getShopOrderTypeList(Integer shopId, String type, Integer pageNum, Integer pageSize);

    ResultResponse<PageInfo> getShopOrderTypeListPc(String role, Integer shopId, String orderNo, String username, String phone, String orderStatus, Date startDate, Date endDate, Integer pageNum, Integer pageSize);

    ResultResponse<PageInfo> getOrderListPc(Integer mgtId, String orderNo, String username, String phone, String orderStatus, int pageNum, int pageSize);

    /**
     * 订单评价
     * @param orderEvalVO
     * @return
     */
    ResultResponse orderEval(OrderEvalVO orderEvalVO);

    /**
     * 评价晒单（初始数据加载）
     * @param orderNo
     * @return
     */
    ResultResponse initOrderEval(String orderNo);
    /**
     * 拒单
     * @param orderId
     * @return
     */
    ResultResponse shopRefuseOrder(Integer orderId);

    /**
     * 退款
     * @param orderId
     * @return
     */
    ResultResponse shopRefundOrder(Integer orderId);

    /**
     * 管理员确认退款
     * @param orderId
     * @return
     */
    ResultResponse adminConfirmRefund(Integer orderId);

    /**
     * 商家手动接单
     * @param orderId
     * @return
     */
    ResultResponse shopAcceptOrder(Integer orderId);

    /**
     * 商家服务完成(->用户+配送员)
     * @param orderId
     * @return
     */
    ResultResponse shopServiceCompleted(Integer orderId);

    /**
     * 获取普通配送员订单
     * @param expressId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> expressOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize);

    /**
     * 获取管理员配送员订单
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse<PageInfo> expressAdminOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize);

    /**
     * 获取商家已接单分布统计
     * @param expressId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultResponse getShopAcceptedOrderStatics(Integer expressId, String type, Integer pageNum, Integer pageSize);

    ResultResponse getRefundData(Integer orderId);

    ResultResponse getConcessionalOrderList(String token, String shopName, String orderNo, String username, String phone, Date startDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 获取订单评论
     * @param orderId
     * @return
     */
    ResultResponse getOrderEvalData(Integer orderId);

    /**
     * 退款
     * @param userId
     * @param orderNo
     * @return
     */
    ResultResponse refund(Integer userId, String orderNo);

    /**
     * 订单详情(商家)
     * @param userId
     * @param orderNo
     * @return
     */
    ResultResponse getShopOrderDetail(Integer userId, String orderNo);

    /**
     * 追加订单
     * @param userId
     * @param shopId
     * @param orderType (4)
     * @return
     */
    ResultResponse<OrderVo> reAddOrder(Integer userId, String orderNo, Integer shopId, Integer orderType, BigDecimal totalPrice);
}
