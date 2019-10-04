package com.chongdao.client.repository;

import com.chongdao.client.entitys.ProviderSeekFavor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderSeekFavorRepository extends JpaRepository<ProviderSeekFavor,Integer> {

    List<ProviderSeekFavor> findByLivingId(Integer livingId);

    ProviderSeekFavor findByLivingIdAndLostUserIdAndProviderUserId(Integer livingId, Integer lostUserId, Integer providerUserId);
}
