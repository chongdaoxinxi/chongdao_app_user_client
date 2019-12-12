package com.chongdao.client.repository;

import com.chongdao.client.entitys.UserTrans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description 用户账户交易记录JPA
 * @Author onlineS
 * @Date 2019/4/28
 * @Version 1.0
 **/
public interface UserTransRepository extends JpaRepository<UserTrans, Integer> {
    Page<UserTrans> findByUserIdAndType(Integer userId, Integer type, Pageable pageable);

    @Query(value="select ut.* from user_trans ut where ut.user_id = ?1 and (ut.type = 4 or ut.type = 5 or ut.type = 6) order by ut.create_time desc limit ?2, ?3", nativeQuery = true)
    List<UserTrans> getRewardUserTrans(Integer userId, Integer pageNum, Integer pageSize);
}
