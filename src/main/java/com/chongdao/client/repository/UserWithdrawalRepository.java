package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWithdrawalRepository extends JpaRepository<UserWithdrawal, Integer> {
    List<UserWithdrawal> findByStatus(Integer status);
}
