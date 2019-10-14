package com.chongdao.client.mapper;

import com.chongdao.client.entitys.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderInfoMapper {
    int deleteByPrimaryKey(OrderInfo key);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(OrderInfo key);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);


    OrderInfo selectByOrderNo(String orderNo);

    OrderInfo selectByUserIdAndOrderNo(@Param("userId")Integer userId, @Param("orderNo")String orderNo);

    List<OrderInfo> selectByUserIdList(@Param("userId") Integer userId, @Param("type")String type);

    List<OrderInfo> selectByShopIdList(@Param("shopId") Integer userId, @Param("type")String type);

    List<OrderInfo> selectByShopIdListPc(@Param("shopId") Integer userId, @Param("orderNo")String orderNo, @Param("username")String username, @Param("phone")String phone, @Param("orderStatus")String orderStatus, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<OrderInfo> getOrderListPc(@Param("areaCode") String areaCode, @Param("orderNo")String orderNo, @Param("username")String username, @Param("phone")String phone, @Param("orderStatus")String orderStatus);

    /**
     * 计算店铺的准时率
     * 获取该店铺所有已完成的订单
     * @param shopId
     * @return
     */
    Integer findByShopIdAll(Integer shopId);

    /**
     *
     * 获取该店铺准时送达的订单
     * @param shopId
     * @return
     */
    Integer findByShopIdPunctuality(Integer shopId);

    List<OrderInfo> selectExpressOrderList(@Param("expressId") Integer expressId, @Param("type")String type);

    List<OrderInfo> selectExpressAdminOrderList(@Param("type")String type);

    List<OrderInfo> getConcessionalOrderList(@Param("areaCode") String areaCode, @Param("shopId") Integer shopId, @Param("shopName") String shopName, @Param("orderNo") String orderNo, @Param("username") String username, @Param("phone") String phone, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}