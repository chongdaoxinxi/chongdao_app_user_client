package com.chongdao.client.repository;

import com.chongdao.client.entitys.LivingCollect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivingCollectRepository extends JpaRepository<LivingCollect, Integer> {

    LivingCollect findByUserIdAndLivingId(Integer userId, Integer livingId);

}
