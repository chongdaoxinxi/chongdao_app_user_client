package com.chongdao.client.repository;

import com.chongdao.client.entitys.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 区域JPA
 */
public interface AreaRepository extends JpaRepository<Area, Integer> {
    /**
     * 查询各级地区
     * @param level
     * @param isOpen
     * @return
     */
    Optional<List<Area>> findByLevelAndIsOpen(Integer level, Integer isOpen);

    /**
     * 查询级联出的地区(根据上级Id查询)
     * @param level
     * @param isOpen
     * @param pId
     * @return
     */
    Optional<List<Area>> findByLevelAndIsOpenAndPid(Integer level, Integer isOpen, Integer pId);
}
