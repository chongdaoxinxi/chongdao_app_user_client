package com.chongdao.client.repository;

import com.chongdao.client.entitys.Express;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpressRepository extends JpaRepository<Express, Integer> {
    List<Express> findByAreaCodeAndStatus(String areaCode, Integer status);

    List<Express> findByIdAndStatus(Integer id, Integer status);

    Optional<Express> findByUsernameAndPassword(String username, String password);

    @Query(value = "select * from express exp where exp.area_code = ?1 and (exp.name like concat('%',?2,'%') or (?2 is null) or (?2 = '')) limit ?3, ?4", nativeQuery = true)
    List<Express> getExpressListByAreaCodeAndExpressName(String areaCode, String expressName, Integer pageNum, Integer pageSize);



}
