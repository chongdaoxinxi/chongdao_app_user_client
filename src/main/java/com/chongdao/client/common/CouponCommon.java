package com.chongdao.client.common;

import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import com.chongdao.client.entitys.coupon.CpnUser;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.repository.coupon.CouponInfoRepository;
import com.chongdao.client.repository.coupon.CpnThresholdRuleRepository;
import com.chongdao.client.repository.coupon.CpnUserRepository;
import com.chongdao.client.service.iml.CouponServiceImpl;
import com.chongdao.client.utils.DateTimeUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author fenglong
 * @date 2019-10-23 15:18
 */

@Component
public class CouponCommon {

    @Autowired
    private CouponInfoRepository couponInfoRepository;
    @Autowired
    private CpnUserRepository cpnUserRepository;

    @Autowired
    private CpnThresholdRuleRepository cpnThresholdRuleRepository;

    //封装优惠券(店铺优惠外)  cpnType == 4
    public List<CouponInfo> couponInfoFullList(Integer shopId){
        //获取满减
        List<CouponInfo> couponInfoList = couponInfoRepository.findByShopIdInAndCpnStateAndCpnTypeOrderByCpnValueDesc(shopId, 1,4);
        List<CouponInfo> couponInfoFullList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(couponInfoList)){
            couponInfoList.stream().forEach(couponInfo -> {
                long result = DateTimeUtil.costTime(DateTimeUtil.dateToStr(couponInfo.getValidityEndDate()),
                        DateTimeUtil.dateToStr(new Date()));
                if (result > 0) {
                    CpnThresholdRule cpnThresholdRule = cpnThresholdRuleRepository.findByCpnId(couponInfo.getId());
                    if (cpnThresholdRule != null && cpnThresholdRule.getMinPrice() != null) {
                        couponInfo.setMinPrice(cpnThresholdRule.getMinPrice());
                    }
                    //设置优惠券限制范围名称
                    CouponServiceImpl.setCouponScope(couponInfo);
                    couponInfoFullList.add(couponInfo);
                }
            });
        }
        return couponInfoFullList;
    }


    //封装优惠券(店铺满减除外) cpnType 不等于 4
    public List<CouponInfo> couponInfoList(Integer shopId,Integer userId){
        List<CouponInfo> couponList = couponInfoRepository.findByShopIdAndCpnStateAndCpnTypeNotOrderByCpnValueDesc(shopId, CouponStatusEnum.COUPON_PUBLISHED.getStatus(),4);
        //优惠券
        List<CouponInfo> cpnInfoList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(couponList)) {
            couponList.stream().forEach(couponInfo -> {
                //二次校验，过滤失效的优惠券
                long result = DateTimeUtil.costTime(DateTimeUtil.dateToStr(couponInfo.getValidityEndDate()),
                        DateTimeUtil.dateToStr(new Date()));
                if (result > 0) {
                    CpnUser cpnUser = cpnUserRepository.findByUserIdAndCpnIdAndShopId(userId, couponInfo.getId(), String.valueOf(shopId));
                    if (cpnUser != null) {
                        //已领取
                        couponInfo.setReceive(1);
                    }
                    //设置优惠券限制范围名称
                    CouponServiceImpl.setCouponScope(couponInfo);
                    cpnInfoList.add(couponInfo);
                }
            });
        }
        return cpnInfoList;
    }
}
