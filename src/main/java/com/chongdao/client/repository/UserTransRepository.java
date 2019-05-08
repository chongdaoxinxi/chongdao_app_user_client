package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserTrans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description 用户账户交易记录JPA
 * @Author onlineS
 * @Date 2019/4/28
 * @Version 1.0
 **/
public interface UserTransRepository extends JpaRepository<UserTrans, Integer> {
    Page<UserTrans> findByUserIdAndType(Integer userId, Integer type, Pageable pageable);
}
