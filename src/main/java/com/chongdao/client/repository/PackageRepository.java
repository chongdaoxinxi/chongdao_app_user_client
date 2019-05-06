package com.chongdao.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chongdao.client.entitys.Package;

/**
 * 大礼包
 */
public interface PackageRepository extends JpaRepository<Package, Integer> {
}
