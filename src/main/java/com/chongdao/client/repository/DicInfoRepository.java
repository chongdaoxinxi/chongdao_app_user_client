package com.chongdao.client.repository;

import com.chongdao.client.entitys.DicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DicInfoRepository extends JpaRepository<DicInfo, Integer> {
    Optional<List<DicInfo>> findByCodeAndAreaCodeAndStatus(String code, String areaCode, Integer status);


    DicInfo findByAreaCodeAndCodeAndStatus(String areaCode, String code, Integer status);
}
