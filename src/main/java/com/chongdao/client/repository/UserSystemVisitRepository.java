package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserSystemVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSystemVisitRepository extends JpaRepository<UserSystemVisit, Integer> {
    List<UserSystemVisit> findByUserId(Integer userId);
}
