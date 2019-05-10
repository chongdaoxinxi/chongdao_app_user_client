package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserShare;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/10
 * @Version 1.0
 **/
public interface UserShareRepository extends JpaRepository<UserShare, Integer> {
    UserShare findByUserId(Integer userId);
}
