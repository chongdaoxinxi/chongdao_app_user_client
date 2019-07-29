package com.chongdao.client.repository;

import com.chongdao.client.entitys.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Integer> {

    Optional<List<Unit>> findByType(Integer type);

    /**
     * 获取符合当前服务的规格单位数据
     * @param categoryId
     * @return
     */
    @Query(value="select unit.* from unit where find_in_set(?1, unit.category_id_list)", nativeQuery = true)
    List<Unit> getUnitFindInSetCategoryIdList(Integer categoryId);






}
