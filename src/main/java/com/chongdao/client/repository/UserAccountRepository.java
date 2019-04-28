package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description 用户账户JPA
 * @Author onlineS
 * @Date 2019/4/28
 * @Version 1.0
 **/
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    /**
     * 根据用户id查询账户信息
     * @param userId
     * @return
     */
    UserAccount findByUserId(Integer userId);
}
