package com.chongdao.client.repository;

import com.chongdao.client.entitys.BathingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BathingServiceRepository extends JpaRepository<BathingService,Integer> {

    @Query(value = "select * from bathing_service where id in (?1)",nativeQuery = true)
    List<BathingService> findByIdIn(String ids);
}
