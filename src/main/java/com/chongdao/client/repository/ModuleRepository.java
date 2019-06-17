package com.chongdao.client.repository;

import com.chongdao.client.entitys.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Integer> {
    Optional<List<Module>> findByStatus(Integer status);
}
