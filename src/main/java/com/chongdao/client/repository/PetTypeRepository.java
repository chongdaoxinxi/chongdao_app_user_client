package com.chongdao.client.repository;

import com.chongdao.client.entitys.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description 宠物种类JPA
 * @Author onlineS
 * @Date 2019/4/23
 * @Version 1.0
 **/
public interface PetTypeRepository extends JpaRepository<PetType, Integer> {
}
