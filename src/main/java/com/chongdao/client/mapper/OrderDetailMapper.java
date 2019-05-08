package com.chongdao.client.mapper;

import com.chongdao.client.entitys.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    void batchInsert(@Param("orderItemList") List<OrderDetail> orderItemList);

    List<OrderDetail> getByOrderNoUserId(@Param("orderNo") String orderNo, @Param("userId") Integer userId);

    List<OrderDetail> getByOrderNo(String orderNo);
}