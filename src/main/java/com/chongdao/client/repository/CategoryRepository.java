package com.chongdao.client.repository;

import com.chongdao.client.entitys.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByStatus(Integer status);

    List<Category> findAllByStatus(Integer status);

    /**
     * 查询商品分类
     * @param type
     * @param status
     * @return
     */
    List<Category> findAllByTypeAndStatus(Integer type, Integer status);
}
