package com.chongdao.client.repository;

import com.chongdao.client.entitys.RecommendInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendInfoRepository extends JpaRepository<RecommendInfo, Integer> {
    List<RecommendInfo> findByRecommenderIdAndType(Integer recommendId, Integer type);

    List<RecommendInfo> findByRecommendCode(String recommendCode);
}
