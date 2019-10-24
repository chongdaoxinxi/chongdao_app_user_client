package com.chongdao.client.mapper;

import com.chongdao.client.vo.OrderInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderInfoVOMapper {

    List<OrderInfoVO> selectByOrderNos( @Param("orderNos") List<String> orderNos,@Param("userId") Integer userId);
}
