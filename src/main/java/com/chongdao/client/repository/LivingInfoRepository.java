package com.chongdao.client.repository;

import com.chongdao.client.entitys.LivingInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivingInfoRepository extends JpaRepository<LivingInfo,Integer> {

    Page<List<LivingInfo>> findByPetTypeIdAndStatus(Integer petTypeId, Pageable pageable, Integer status);

    Page<List<LivingInfo>> findByPetTypeIdAndStatusOrderByPriceAsc(Integer petTypeId, Pageable pageable, Integer status);

    Page<List<LivingInfo>> findByPetTypeIdAndStatusOrderByPriceDesc(Integer petTypeId, Pageable pageable, Integer status);



    List<LivingInfo> findByTypeAndStatusAndUserId(Integer type, Integer status, Integer userId);


    List<LivingInfo> findByTypeAndStatus(Integer type, Integer status);

}
