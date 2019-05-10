package com.chongdao.client.repository;

import com.chongdao.client.entitys.PackageCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 礼包优惠券关联表JPA
 */
public interface PackageCardRepository extends JpaRepository<PackageCard, Integer> {
    Optional<List<PackageCard>> findByPackageId(Integer integer);
}
