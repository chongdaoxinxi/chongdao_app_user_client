package com.chongdao.client.controller.coupon;

import com.chongdao.client.common.CouponScopeCommon;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.entitys.coupon.CpnUser;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.repository.coupon.CouponInfoRepository;
import com.chongdao.client.repository.coupon.CpnUserRepository;
import com.chongdao.client.service.CouponService;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/coupon/")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponInfoRepository couponInfoRepository;

    @Autowired
    private CpnUserRepository cpnUserRepository;


    /**
     * 订单优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(0：商品以及服务优惠券 1: 配送券)
     * @param serviceType 服务类型 1.双程 2.单程 3.到店自取
     * @param categoryId 检验商品类型（主要是针对服务和商品的优惠券使用场景）
     * @return
     */
    @PostMapping("getCouponListByShopId/{shopId}/{type}")
    public ResultResponse getCouponListByShopId(@PathVariable String shopId,
                                                @PathVariable Integer type,
                                                String categoryId,
                                                BigDecimal totalPrice,
                                                Integer serviceType,
                                                String token){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return couponService.getCouponListByShopIdAndType(tokenVo.getUserId(),shopId,categoryId,totalPrice,type,serviceType);
    }


    /**
     * 领取优惠券
     * 注：此接口领取含特殊优惠券（如：仅限哪几家店铺使用等场景，需要去pc管理后台设置）
     * @return
     */
    @PostMapping("receiveCoupon")
    public ResultResponse receiveCoupon(@RequestBody CouponInfo couponInfo){
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(couponInfo.getToken());
        return couponService.receiveCoupon(tokenVo.getUserId(), couponInfo);
    }

    /**
     * 卡包
     * @param userId
     * @param token
     * @return
     */
    @GetMapping("couponList/{userId}")
    public ResultResponse couponList(@PathVariable Integer userId,@RequestParam String token){
        LoginUserUtil.resultTokenVo(token);
        return couponService.couponList(userId);
    }


    /**
     * "领券" 列表
     * @param shopId
     * @param userId
     * @return
     */
    @GetMapping("receiveNotCpnList/{shopId}/{userId}")
    public ResultResponse receiveNotCpnList(@PathVariable Integer shopId, @PathVariable Integer userId){
        //封装优惠券(店铺满减除外(cpnType = 4))
        List<CouponInfo> couponList = couponInfoRepository.findByShopIdAndCpnStateAndCpnTypeNotOrderByCpnValueDesc(shopId, CouponStatusEnum.COUPON_PUBLISHED.getStatus(),4);
        List<CouponInfo> couponInfoList = Lists.newArrayList();
        couponList.stream().forEach(e ->{
            //二次校验，过滤失效的优惠券
            long result = DateTimeUtil.costTime(DateTimeUtil.dateToStr(e.getValidityEndDate()),
                    DateTimeUtil.dateToStr(new Date()));
            if (result > 0){
                //需判断当前用户是否已领取该优惠券
                CpnUser cpnUser = cpnUserRepository.findByUserIdAndCpnIdAndShopId(userId, e.getId(), String.valueOf(shopId));
                if (cpnUser != null) {
                    //已领取
                    e.setReceive(1);
                }
                if (e.getScopeType() != null) {
                    e.setScopeName(CouponScopeCommon.cpnScope(e.getScopeType(),e).getScopeName());
                }
                couponInfoList.add(e);
            }
        });
        return ResultResponse.createBySuccess(couponInfoList);

    }


}
