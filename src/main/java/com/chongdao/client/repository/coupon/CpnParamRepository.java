package com.chongdao.client.repository.coupon;

import com.chongdao.client.entitys.CpnParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CpnParamRepository extends JpaRepository<CpnParam,Integer> {
    //获取适用范围 (管理员)
    List<CpnParam> findByScopeTypeNotNull();
    //获取优惠类别 (管理员)
    List<CpnParam> findByCpnTypeNotNull();

    //获取适用范围 (管理员)
    List<CpnParam> findByScopeTypeNotNullAndParamType(Integer paramType);
    //获取优惠类别 (管理员)
    List<CpnParam> findByCpnTypeNotNullAndParamType(Integer paramType);
}
