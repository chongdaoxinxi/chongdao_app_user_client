package com.chongdao.client.repository;

import com.chongdao.client.entitys.PetBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetBreedRepository extends JpaRepository<PetBreed, Integer> {
    @Query(value = "select * from pet_card pc where pc.type = ?1 and pc.name like concat('%',?2,'%') limit 0, 10", nativeQuery = true)
    List<PetBreed> selectByTypeAndName(Integer type, String name);
}
