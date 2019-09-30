package com.chongdao.client.mapper;

import com.chongdao.client.vo.RecommendRankVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface RecommendMapper {
    List<RecommendRankVO> getRecommendRankList();

    BigDecimal getMyRecommendTotalMoney(@Param("recommenderId") Integer recommenderId);
}
