package com.chongdao.client.repository;

import com.chongdao.client.entitys.Support;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportRepository extends JpaRepository<Support, Integer> {

    Support findByUserIdAndLivingId(Integer userId, Integer livingId);
}
