package com.chongdao.client.repository;

import com.chongdao.client.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByName(String name);

    /**
     * 根据用户名更新用户更新时间
     * @param lastLoginTime
     * @param username
     */
    @Modifying
    @Transactional
    @Query(value = "update user set last_login_time = ?1 where name=?2",nativeQuery = true)
    void updateLastLoginTimeByName(Date lastLoginTime, String username);
}
