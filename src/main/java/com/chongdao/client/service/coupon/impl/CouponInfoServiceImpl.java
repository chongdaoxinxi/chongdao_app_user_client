package com.chongdao.client.service.coupon.impl;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.CouponScopeCommon;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.GoodsType;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.entitys.coupon.CouponScopeRule;
import com.chongdao.client.entitys.coupon.CpnSuperpositionRule;
import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.service.coupon.CouponInfoService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

import static com.chongdao.client.common.CouponConst.*;

/**
 * @author fenglong
 * @date 2019-06-18 17:04
 */

@Service
public class CouponInfoServiceImpl extends CommonRepository implements CouponInfoService {

    /**
     * 添加优惠券
     * @param couponInfo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultResponse add(CouponInfo couponInfo) {
        //添加优惠券
        couponInfo.setCreateDate(new Date());
        couponInfo.setUpdateDate(new Date());
        couponInfoRepository.save(couponInfo);
        //优惠券叠加
        this.saveCpnSuperpositionRule(couponInfo);
        //门槛规则叠加
        if (couponInfo.getRuleType() == THRESHOLD) {//有门槛
            this.saveCpnThresholdRule(couponInfo);
        }
        //适用范围类型
        this.saveCouponScopeRule(couponInfo);
        return ResultResponse.createBySuccess();
    }

    /**
     * 获取分类
     * @param categoryId 3限商品 4限服务 5 全部
     * @return
     */
    @Override
    public ResultResponse getCategory(Integer categoryId) {
        //限制商品类，查询该分类下的所有商品
        if (categoryId != null && categoryId == LIMITED_GOODS){
            List<GoodsType> goodsTypeList = goodsTypeRepository.findAllByCategoryIdAndStatus(3, 1);
            return ResultResponse.createBySuccess(goodsTypeList);
        }else if (categoryId != null && categoryId == LIMITED_SERVICE) {
            //服务
            return ResultResponse.createBySuccess(goodsTypeRepository.findByCategoryIdNotAndStatus(3, 1));
        }else {
            //全部
            return ResultResponse.createBySuccess(goodsTypeRepository.findAll());
        }

    }


    /**
     * 根据商品id更新相应状态
     * @param cpnId
     * @param state 状态-1 已删除 0待发布 1已发布 2已下架
     * @return
     */
    @Transactional
    @Override
    public ResultResponse updateState(Integer cpnId, Integer state) {
        if (cpnId == null || state == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        //如果删除，其余表也要更新状态
        if (state == -1){
            //修改优惠券叠加规则 删除
            superpositionRuleRepository.updateState(cpnId);
            //修改门槛规则 删除
            thresholdRuleRepository.updateState(cpnId);
            //修改适用范围类型 删除
            scopeRuleRepository.updateState(cpnId);
            //修改用户领取优惠券表 删除
            cpnUserRepository.updateState(cpnId);
        }
        couponInfoRepository.updateState(cpnId,state);
        return ResultResponse.createBySuccess();
    }

    /**
     * 查询商家所有优惠券
     * @param shopId
     * @return
     */
    @Override
    public ResultResponse list(Integer shopId,Integer state,Integer cpnType,Integer goodsTypeId) {
        if (shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        List<CouponInfo> couponInfoList = Lists.newArrayList();
        if (cpnType == 1) {
            //满减
            couponInfoList = couponInfoRepository.findAllByShopIdAndCpnStateAndCpnType(shopId, state, 4);
        }else {
            //优惠券
            if (goodsTypeId != null) {
                //根据条件筛选相应的优惠券
                List<CouponScopeRule> couponScopeRuleList = scopeRuleRepository.findByCategoryId(goodsTypeId);
                if (!CollectionUtils.isEmpty(couponScopeRuleList)) {
                    for (CouponScopeRule couponScopeRule : couponScopeRuleList) {
                       CouponInfo couponInfo = couponInfoRepository.findByShopIdAndIdAndCpnStateAndCpnTypeNot(shopId,couponScopeRule.getCpnId(),  state, cpnType);
                       if (couponInfo != null) {
                           couponInfoList.add(couponInfo);
                       }
                    }
                }
            }else {
                couponInfoList = couponInfoRepository.findAllByShopIdAndCpnStateAndCpnTypeNot(shopId, state, 4);
            }
        }
        if (!CollectionUtils.isEmpty(couponInfoList)) {
            couponInfoList.stream().forEach(couponInfo -> {
                if (couponInfo.getScopeType() != null) {
                    couponInfo.setScopeName(CouponScopeCommon.cpnScope(couponInfo.getScopeType(),couponInfo).getScopeName());
                }
            });
        }
        return ResultResponse.createBySuccess(couponInfoList);
    }


    /**
     * 添加优惠券叠加规则
     * @param couponInfo
     */
    private void saveCpnSuperpositionRule(CouponInfo couponInfo){
        CpnSuperpositionRule rule = new CpnSuperpositionRule();
        rule.setSpnCpnType(couponInfo.getCpnType());
        rule.setCpnId(couponInfo.getId());
        rule.setCreateDate(new Date());
        rule.setUpdateDate(new Date());
        rule.setIsDelete(0);
        superpositionRuleRepository.save(rule);
    }

    /**
     * 添加门槛规则
     * @param couponInfo
     */
    private void saveCpnThresholdRule(CouponInfo couponInfo){
        CpnThresholdRule cpnThresholdRule = new CpnThresholdRule();
        cpnThresholdRule.setCpnId(couponInfo.getId());
        cpnThresholdRule.setMaxPrice(couponInfo.getMaxPrice());
        cpnThresholdRule.setMinPrice(couponInfo.getMinPrice());
        cpnThresholdRule.setCreateDate(new Date());
        cpnThresholdRule.setUpdateDate(new Date());
        cpnThresholdRule.setIsDelete(0);
        thresholdRuleRepository.save(cpnThresholdRule);
    }

    /**
     * 添加适用范围类型
     * @param couponInfo
     */
    private void saveCouponScopeRule(CouponInfo couponInfo){
        CouponScopeRule couponScopeRule = new CouponScopeRule();
        couponScopeRule.setCpnId(couponInfo.getId());
        couponScopeRule.setKindName(couponInfo.getScopeTypeName());
        couponScopeRule.setGoodsId(couponInfo.getGoodsId());
        couponScopeRule.setCategoryId(couponInfo.getCategoryId());
        couponScopeRule.setScopeType(couponInfo.getScopeType());
        couponScopeRule.setCreateDate(new Date());
        couponScopeRule.setUpdateDate(new Date());
        couponScopeRule.setIsDelete(0);
        scopeRuleRepository.save(couponScopeRule);
    }
}
