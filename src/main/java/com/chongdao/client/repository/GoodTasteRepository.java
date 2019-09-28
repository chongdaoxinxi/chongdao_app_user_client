package com.chongdao.client.repository;

import com.chongdao.client.entitys.GoodTaste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodTasteRepository extends JpaRepository<GoodTaste, Integer> {
    List<GoodTaste> findAllByOrderByCreateTimeDesc();
}
