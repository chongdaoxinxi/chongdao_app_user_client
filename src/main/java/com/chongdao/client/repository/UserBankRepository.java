package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserBankRepository extends JpaRepository<UserBank, Integer> {
    List<UserBank> findByUserId(Integer userId);
}
