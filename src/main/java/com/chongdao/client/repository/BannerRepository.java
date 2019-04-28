package com.chongdao.client.repository;

import com.chongdao.client.entitys.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description 轮播图JPA
 * @Author onlineS
 * @Date 2019/4/23
 * @Version 1.0
 **/
public interface BannerRepository extends JpaRepository<Banner, Integer> {
    /**
     * 根据区域码查询
     * @param areaCode
     * @return
     */
    List<Banner> findByAreaCode(String areaCode);
}
