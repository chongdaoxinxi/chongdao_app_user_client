package com.chongdao.client.repository;

import com.chongdao.client.entitys.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
