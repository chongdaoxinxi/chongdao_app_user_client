package com.chongdao.client.service.coupon.impl;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Category;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.entitys.coupon.CouponScopeRule;
import com.chongdao.client.entitys.coupon.CpnSuperpositionRule;
import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import com.chongdao.client.service.coupon.CouponInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.chongdao.client.common.CouponConst.LIMITED_GOODS;
import static com.chongdao.client.common.CouponConst.THRESHOLD;

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
     * @param categoryId 3限商品 4限服务
     * @return
     */
    @Override
    public ResultResponse getCategory(Integer categoryId) {
        //限制商品类，查询该分类下的所有商品
        if (categoryId != null && categoryId == LIMITED_GOODS){
            List<Category> categoryList = categoryRepository.findAllByTypeAndStatus(2, 1);
            return ResultResponse.createBySuccess(categoryList);
        }
        //服务
        return ResultResponse.createBySuccess(categoryRepository.findAllByTypeAndStatus(1, 1));
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
        couponScopeRule.setKindId(couponInfo.getGoodsId());
        couponScopeRule.setCategoryId(couponInfo.getCategoryId());
        couponScopeRule.setScopeType(couponInfo.getScopeType());
        couponScopeRule.setCreateDate(new Date());
        couponScopeRule.setUpdateDate(new Date());
        couponScopeRule.setIsDelete(0);
        scopeRuleRepository.save(couponScopeRule);
    }
}
