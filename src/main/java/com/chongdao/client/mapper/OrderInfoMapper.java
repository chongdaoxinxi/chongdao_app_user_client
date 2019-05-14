package com.chongdao.client.mapper;

import com.chongdao.client.entitys.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    OrderInfo selectByUserIdAndOrderNo(@Param("userId")Integer userId, @Param("payOutTradeNo")String payOutTradeNo);

    List<OrderInfo> selectByUserIdList(@Param("userId") Integer userId, @Param("type")String type);

    List<OrderInfo> selectByShopIdList(@Param("shopId") Integer userId, @Param("type")String type);

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
}