package com.chongdao.client.repository;

import com.chongdao.client.entitys.PetBreed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetBreedRepository extends JpaRepository<PetBreed, Integer> {
    List<PetBreed> findByType(Integer type);
}
