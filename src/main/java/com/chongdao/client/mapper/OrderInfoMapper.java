package com.chongdao.client.mapper;


import com.chongdao.client.entitys.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(OrderInfo key);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(OrderInfo key);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}