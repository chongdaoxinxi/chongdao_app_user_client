package com.chongdao.client.repository;

import com.chongdao.client.entitys.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
public interface ShopRespository extends JpaRepository<Shop, Integer> {
    Optional<Shop> findByAccountName(String name);
}
