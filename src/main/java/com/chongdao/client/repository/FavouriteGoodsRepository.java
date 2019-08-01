package com.chongdao.client.repository;

import com.chongdao.client.entitys.FavouriteGood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteGoodsRepository extends JpaRepository<FavouriteGood,Integer> {

    FavouriteGood findByUserIdAndStatusAndGoodId(Integer userId, Integer status, Integer goodsId);

    Optional<List<FavouriteGood>> findAllByUserIdAndStatus(Integer userId, Integer status);

    int countByUserIdAndGoodIdAndStatus(Integer userId,Integer goodId,Integer status);

}
