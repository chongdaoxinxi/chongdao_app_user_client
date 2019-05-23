package com.chongdao.client.repository;

import com.chongdao.client.entitys.Express;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpressRepository extends JpaRepository<Express, Integer> {
    List<Express> findByAreaCodeAndStatus(String areaCode, Integer status);

    List<Express> findByIdAndStatus(Integer id, Integer status);

    Optional<Express> findByUsernameAndPassword(String username, String password);
}
