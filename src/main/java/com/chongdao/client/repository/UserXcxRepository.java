package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserXcx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserXcxRepository extends JpaRepository<UserXcx, Integer> {
    List<UserXcx> findByPhone(String phone);
}
