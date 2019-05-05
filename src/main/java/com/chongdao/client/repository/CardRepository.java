package com.chongdao.client.repository;

import com.chongdao.client.entitys.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {

    /**
     * 查看单程配送券列表
     * @param userId
     * @return
     */
    @Query(value ="select c2.* from card_user c1 inner join card c2 on c1.card_id = c2.id where c2.type in (1,3) and c2.status = 1 and c1.count > 0 and end_time > NOW() " +
            "AND c1.user_id=?1",nativeQuery = true)
    List<Card> findByUserIdAndSingleTripList(Integer userId);


    /**
     * 查看双程配送券列表
     * @param userId
     * @return
     */
    @Query(value = "select c2.* from card_user c1 inner join card c2 on c1.card_id = c2.id where c2.type in (1,4) and c2.status = 1 and c1.count > 0 and end_time > NOW() " +
            "AND c1.user_id=?1",nativeQuery = true)
    List<Card> findByUserIdAndRoundTripList(Integer userId);
}
