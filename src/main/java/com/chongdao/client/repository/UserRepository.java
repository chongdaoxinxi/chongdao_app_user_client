package com.chongdao.client.repository;

import com.chongdao.client.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

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


    @Query(value = "select count(1) from user where name= ?1",nativeQuery = true)
    int checkUserName(String str);

    List<User> findByPhone(String phone);

    List<User> findByRecommendIdAnAndRecommendType(Integer recommendId, Integer recommendType);
}
