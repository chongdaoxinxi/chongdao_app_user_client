package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserRegInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegInfoRepository extends JpaRepository<UserRegInfo, Integer> {
    UserRegInfo findByUserId(Integer userId);
}
