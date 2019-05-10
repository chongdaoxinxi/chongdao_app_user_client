package com.chongdao.client.repository;

import com.chongdao.client.entitys.PackageUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 礼包用户关联表
 */
public interface PackageUserRepository extends JpaRepository<PackageUser, Integer> {
}
