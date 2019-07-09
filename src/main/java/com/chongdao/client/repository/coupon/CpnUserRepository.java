package com.chongdao.client.repository.coupon;

import com.chongdao.client.entitys.coupon.CpnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CpnUserRepository extends JpaRepository<CpnUser,Integer> {
    @Transactional
    @Modifying
    @Query(value = "update cpn_user set is_delete=1 where cpn_id=?1",nativeQuery = true)
    void updateState(Integer cpnId);

    Iterable<CpnUser> findAllByUserCpnState(Integer state);




    //-------------------------------  用户端  ------------------------------------

    //查询当前用户是否已领取当前优惠券
    CpnUser findByUserIdAndCpnIdAndShopId(Integer userId, Integer cpnId, String shopId);

    //查询符合当前用户的优惠券个数
    int countByUserIdAndCpnIdAndShopId(Integer userId, Integer cpnId, String shopId);

    //查询当前用户的优惠券列表（限制条件:未使用and未删除）
    List<CpnUser> findByShopIdAndUserIdAndUserCpnStateAndIsDeleteAndCpnTypeInAndCpnScopeTypeIn(String shopId, Integer userId,
                                                                  Integer cpnState,Integer isDelete,List<Integer> cpnType,List<Integer> scopeTypes);


    /***
     * 获取优惠券数量
     * @param userId
     * @param delete
     * @param cpnType
     * @return
     */
    int countByUserIdAndIsDeleteAndCpnType(Integer userId, Integer delete,Integer cpnType);

    int countByUserIdAndIsDeleteAndCpnTypeIn(Integer userId, Integer delete,List<Integer> cpnTypes);


    /**
     * 获取有效状态的优惠券（卡包）
     * @param userId
     * @param states
     * @param isDelete
     * @return
     */
    List<CpnUser> findByUserIdAndUserCpnStateAndIsDelete(Integer userId,Integer states,Integer isDelete);




}
