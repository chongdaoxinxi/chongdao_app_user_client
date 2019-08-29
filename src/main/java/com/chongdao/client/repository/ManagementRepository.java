package com.chongdao.client.repository;

import com.chongdao.client.entitys.Management;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagementRepository extends JpaRepository<Management, Integer> {
    Optional<List<Management>> findByNameAndPasswordAndStatus(String username, String password, Integer status);
    List<Management> findByLevel(Integer level);
    List<Management> findByAreaCodeAndStatus(String areaCode, Integer status);
}
