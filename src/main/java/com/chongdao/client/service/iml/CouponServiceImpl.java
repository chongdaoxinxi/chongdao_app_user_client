package com.chongdao.client.service.iml;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import com.chongdao.client.entitys.coupon.CpnUser;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.CardRepository;
import com.chongdao.client.repository.CardUserRepository;
import com.chongdao.client.repository.CouponUserRepository;
import com.chongdao.client.service.CouponService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.chongdao.client.common.CouponConst.*;

@Service
public class CouponServiceImpl extends CommonRepository implements CouponService {



    @Autowired
    private CardUserRepository cardUserRepository;

    @Autowired
    private CouponUserRepository couponUserRepository;

    @Autowired
    private CardRepository cardRepository;





    /**
     * 订单优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(0：商品以及服务优惠券 1: 配送优惠券)
     * @param serviceType 服务类型 1.双程 2.单程 3.到店自取
     * @param categoryId 商品（服务）分类0,1
     * @param totalPrice 订单金额（商品或者服务折扣后的价格）
     * @return
     */
    @Override
    public ResultResponse getCouponListByShopIdAndType(Integer userId, String shopId, String categoryId,
                                                       BigDecimal totalPrice, Integer type,
                                                       Integer serviceType) {
        List<CpnUser> cpnUsers = Lists.newArrayList();
        //商品以及服务优惠券 类型为 1现金券（红包）3折扣券
        if (type.equals(CouponStatusEnum.COUPON_GOODS.getStatus())) {
            //查询优惠券列表(商品and服务) cpnScopeType: 1全场通用 3限商品 4限服务
            //cpnType:优惠券类型 1现金券 2满减券 3折扣券 4店铺满减
            List<CpnUser> cpnUserList = cpnUserRepository.findByShopIdAndUserIdAndUserCpnStateAndIsDeleteAndCpnTypeInAndCpnScopeTypeIn(shopId, userId, 0, 0,Arrays.asList(1,2,3), Arrays.asList(1,3,4));
            List<CouponInfo> couponInfoList = Lists.newArrayList();
            cpnUserList.stream().forEach(cpnUser -> {
                //逻辑处理
                this.getCpnUser(cpnUser,totalPrice,categoryId);
                CouponInfo couponInfo = couponInfoRepository.findById(cpnUser.getCpnId()).get();
                if (cpnUser.getEnabled() == 1){//优惠券可用
                    couponInfo.setEnabled(1);
                }
                couponInfoList.add(couponInfo);
            });


            return ResultResponse.createBySuccess(couponInfoList);
        }
        //配送优惠券(双程)
        if (serviceType == 1){
            //6配送双程  8仅限服务（双程）10仅限商品（配送双程）
            List<CpnUser> cpnUserList = cpnUserRepository.findByShopIdAndUserIdAndUserCpnStateAndIsDeleteAndCpnTypeInAndCpnScopeTypeIn(shopId, userId, 0, 0, Arrays.asList(1,2,3),Arrays.asList(6, 8, 10));
            cpnUserList.stream().forEach(cpnUser -> {
                    //优惠券可用
                    cpnUser.setEnabled(1);
            });
            return ResultResponse.createBySuccess(cpnUserList);
        }else if (serviceType == 2){//单程
            //5配送单程  7仅限服务（单程）9仅限商品（配送单程）
            List<CpnUser> cpnUserList =cpnUserRepository.findByShopIdAndUserIdAndUserCpnStateAndIsDeleteAndCpnTypeInAndCpnScopeTypeIn(shopId, userId, 0, 0,Arrays.asList(1,2,3), Arrays.asList(5,7,9));
            cpnUserList.stream().forEach(cpnUser -> {
                    //优惠券可用
                    cpnUser.setEnabled(1);
            });
            return ResultResponse.createBySuccess(cpnUserList);
        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 领取优惠券
     * @param userId
     * @param couponInfo
     * @return
     */
    @Override
    @Transactional
    public ResultResponse receiveCoupon(Integer userId, CouponInfo couponInfo) {
        if (userId == null || couponInfo.getShopId() == null || couponInfo.getId() == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),
                    ResultEnum.PARAM_ERROR.getMessage());
        }
        //判断该优惠券是否被领取过
        CpnUser cpnUser = cpnUserRepository.findByUserIdAndCpnIdAndShopId(userId, couponInfo.getId(), String.valueOf(couponInfo.getShopId()));
        if (cpnUser != null){
            return ResultResponse.createByErrorCodeMessage(CouponStatusEnum.RECEIVED_COUPON_CARD.getStatus(),
                    CouponStatusEnum.RECEIVED_COUPON_CARD.getMessage());
        }
        //领取数量+1
        couponInfoRepository.updateReceiveCountByCouponId(couponInfo.getId(),String.valueOf(couponInfo.getShopId()));
        //插入用户优惠券表记录
        cpnUser = new CpnUser();
        cpnUser.setCount(1);
        cpnUser.setCpnBatchId(couponInfo.getBatchId());
        cpnUser.setCpnCode(couponInfo.getCpnCode());
        cpnUser.setCpnId(couponInfo.getId());
        cpnUser.setCpnScopeType(couponInfo.getScopeType());
        cpnUser.setCpnType(couponInfo.getCpnType());
        cpnUser.setCpnValue(couponInfo.getCpnValue());
        cpnUser.setGainDate(new Date());
        cpnUser.setGainDesc(couponInfo.getCpnDesc());
        cpnUser.setRuleType(couponInfo.getRuleType());

        //默认未删除
        cpnUser.setIsDelete(0);
        //默认未使用
        cpnUser.setUserCpnState(0);
        cpnUser.setUseDesc("");
        cpnUser.setValidityStartDate(couponInfo.getValidityStartDate());
        cpnUser.setValidityEndDate(couponInfo.getValidityEndDate());
        cpnUser.setCreateDate(new Date());
        cpnUser.setUpdateDate(new Date());
        cpnUser.setUserId(userId);
        cpnUser.setShopId(String.valueOf(couponInfo.getShopId()));
        cpnUserRepository.save(cpnUser);
        return ResultResponse.createBySuccess();
    }


    /**
     * 封装优惠券逻辑代码（商品和服务）
     * @param cpnUser
     * @return
     */
    private CpnUser getCpnUser(CpnUser cpnUser, BigDecimal totalPrice, String categoryId){
        //获取使用范围
        Integer cpnScopeType = cpnUser.getCpnScopeType();
        //获取门槛规则，当前需要满足的条件
        CpnThresholdRule cpnRule = thresholdRuleRepository.findByCpnId(cpnUser.getCpnId());
        if (cpnScopeType == EXPERT && cpnUser.getRuleType() == NO_THRESHOLD){//全场通用（无门槛）
            cpnUser.setEnabled(1);
        }else if (cpnScopeType == EXPERT && cpnUser.getRuleType() == THRESHOLD){//全场通用（有门槛）
            //满减判断
            this.compare(cpnUser,totalPrice,cpnRule.getMinPrice());
        }else if (cpnScopeType == LIMITED_GOODS && categoryId.contains("0")){//限制商品 categoryId包含0即可
            //无门槛
            if (cpnUser.getRuleType() == NO_THRESHOLD){
                cpnUser.setEnabled(1);
            }else {//有门槛
                //满减判断
                this.compare(cpnUser, totalPrice, cpnRule.getMinPrice());
            }
        }else if (cpnScopeType == LIMITED_SERVICE && categoryId.contains("1")){//限制服务 categoryId包含1即可
            //无门槛
            if (cpnUser.getRuleType() == NO_THRESHOLD){
                cpnUser.setEnabled(1);
            }else {//有门槛
                //满减判断
                this.compare(cpnUser, totalPrice, cpnRule.getMinPrice());
            }
        }
        return cpnUser;
    }

    /**
     * 满减判断
     * @param cpnUser
     * @param totalPrice
     * @param conditionPrice
     * @return
     */
    private CpnUser compare(CpnUser cpnUser,BigDecimal totalPrice,BigDecimal conditionPrice){
        if (totalPrice.compareTo(conditionPrice) == 0 || totalPrice.compareTo(conditionPrice) == 1){
            cpnUser.setEnabled(1);
        }
        return cpnUser;
    }

}
