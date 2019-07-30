package com.chongdao.client.repository;


import com.chongdao.client.entitys.FavouriteShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author fenglong
 * @date 2019-07-29 17:48
 */
public interface FavouriteShopRepository extends JpaRepository<FavouriteShop,Integer> {
    Optional<List<FavouriteShop>> findAllByUserIdAndStatus(Integer userId, Integer status);

    FavouriteShop findByUserIdAndStatusAndShopId(Integer userId, Integer status,Integer shopId);

}
