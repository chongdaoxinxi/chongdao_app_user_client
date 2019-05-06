package com.chongdao.client.mapper;

import com.chongdao.client.vo.CardUserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardUserMapper {
    /**
     *
     * @param userId
     * @param type 1:优惠券;2:配送券;
     * @return
     */
    List<CardUserVo> getCardUserVoByUserId(Integer userId, Integer type);
}
