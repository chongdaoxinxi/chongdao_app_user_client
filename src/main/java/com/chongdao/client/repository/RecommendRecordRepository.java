package com.chongdao.client.repository;

import com.chongdao.client.entitys.RecommendRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRecordRepository extends JpaRepository<RecommendRecord, Integer> {
    List<RecommendRecord> findByUserIdAndConsumeId(Integer userId, Integer consumeId);

    List<RecommendRecord> findByRecommenderIdAndRecommendTypeAndConsumeType(Integer recommenderId, Integer recommendType, Integer consumeType);

    List<RecommendRecord> findByUserIdAndRecommenderIdAndRecommendType(Integer userId, Integer recommenderId, Integer recommendType);
}
