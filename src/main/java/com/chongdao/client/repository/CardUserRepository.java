package com.chongdao.client.repository;

import com.chongdao.client.entitys.CardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardUserRepository extends JpaRepository<CardUser, Integer> {

    //---------------------------------官方券(配送和通用)------------------------------------------------//

    /**
     * 查看双程配送券数量
     * @param userId
     * @return
     */
    @Query(value = "select count(1) from card_user c1 inner join card c2 on c1.card_id = c2.id where c2.type in (1,4) and c2.status = 1 and c1.count > 0 and end_time > NOW() " +
            "AND c1.user_id=?1",nativeQuery = true)
    Integer findByUserIdAndRoundTrip(Integer userId);

    /**
     * 查看单程配送券数量
     * @param userId
     * @return
     */
    @Query(value ="select count(1) from card_user c1 inner join card c2 on c1.card_id = c2.id where c2.type in (1,3) and c2.status = 1 and c1.count > 0 and end_time > NOW() " +
            "AND c1.user_id=?1",nativeQuery = true)
    Integer findByUserIdAndSingleTrip(Integer userId);

    //---------------------------------官方券(商品)------------------------------------------------//

    /**
     * 查看商品优惠券
     * 区分优惠券限制（仅限店铺使用和不限店铺使用）
     * 仅限店铺使用时:card表shop_ids不为空，否则为null
     * @return
     */
    @Query(value ="select count(1) from card_user c1 inner join card c2 on c1.card_id = c2.id where c2.type = 2 and trim(c2.shop_ids)='' and c2.status = 1 and c1.count > 0 and end_time > NOW() " +
            "AND c1.user_id=?1",nativeQuery = true)
    Integer findByUserIdAndShopIdsIsNull(Integer userId);


    @Query(value ="select count(1) from card_user c1,card c2 where  c1.card_id = c2.id and c1.shop_id in (?2) and c2.shop_ids is not null  and c2.type = 2 and c2.status = 1 and c1.count > 0 and end_time > NOW() " +
            "AND c1.user_id=?1",nativeQuery = true)
    Integer findByUserIdAndShopIdsIsNotNull(Integer userId, String shopIds);

    /**
     * 获取公益优惠券
     * @param userId
     * @return
     */
    @Query(value ="select count(1) from card_user c1 inner join card c2 on c1.card_id = c2.id where c2.type = 5 and c2.status = 1 and c1.count > 0 and end_time > NOW() " +
            "AND c1.user_id=?1",nativeQuery = true)
    Integer findByUserIdCommon(Integer userId);


    /**
     * 查询优惠券是否被领取
     * @param couponId
     * @param shopId
     * @param userId
     * @return
     */
    CardUser findByCouponIdAndShopIdAndUserId(Integer couponId, Integer shopId,Integer userId);



}
