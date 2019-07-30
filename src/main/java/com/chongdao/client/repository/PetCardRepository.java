package com.chongdao.client.repository;

import com.chongdao.client.entitys.PetCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Description 宠物资料卡片JPA
 * @Author onlineS
 * @Date 2019/4/23
 * @Version 1.0
 **/
public interface PetCardRepository extends JpaRepository<PetCard, Integer> {
    /**
     * 根据用户id查询卡片
     * @param userId
     * @return
     */
    Optional<List<PetCard>> findByUserId(Integer userId);

    /**
     * 根据用户id查询处于激活状态的卡片
     * @param userId
     * @return
     */
    Optional<List<PetCard>> findByUserIdAndStatus(Integer userId, Integer status);

    List<PetCard> findByUserIdAndStatusAndTypeId(Integer userId, Integer status, Integer typeId);
}
