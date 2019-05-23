package com.chongdao.client.repository;

import com.chongdao.client.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByStatus(Integer status);
}
