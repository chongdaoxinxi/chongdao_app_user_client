package com.chongdao.client.mapper;

import com.chongdao.client.entitys.OrderEval;

import java.util.List;

public interface OrderEvalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderEval record);

    int insertSelective(OrderEval record);

    OrderEval selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderEval record);

    int updateByPrimaryKey(OrderEval record);

    List<OrderEval> getShopEvalAll(Integer shopId);
}