package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.CardUser;
import com.chongdao.client.entitys.Coupon;
import com.chongdao.client.entitys.CouponUser;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.CardUserRepository;
import com.chongdao.client.repository.CouponRepository;
import com.chongdao.client.repository.CouponUserRepository;
import com.chongdao.client.service.CouponService;
import com.chongdao.client.vo.CouponVO;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CardUserRepository cardUserRepository;

    @Autowired
    private CouponUserRepository couponUserRepository;




    /**
     * 商品详情中的优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(0：商品 1: 服务)
     * @return
     */
    @Override
    public ResultResponse<List<CouponVO>> getCouponListByShopIdAndType(Integer userId,Integer shopId, Integer type) {
        List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(shopId, CouponStatusEnum.UP_COUPON.getStatus(), type);
        List<CouponVO> couponVOList = Lists.newArrayList();
        couponList.forEach(coupon -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(coupon, couponVO);
            CouponUser couponUser = couponUserRepository.findByUserIdAndCouponIdAndShopId(userId, coupon.getId(), shopId);
            if (couponUser != null){
                //该优惠券已领取
                couponVO.setReceiveStatus(1);
            }
            couponVOList.add(couponVO);
        });
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(),couponVOList);
    }

    /**
     * 领取优惠券
     * @param userId
     * @param shopId
     * @param couponId
     * @return
     */
    @Override
    public ResultResponse receiveCoupon(Integer userId, Integer shopId, Integer couponId) {
        if (userId == null || shopId == null || couponId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),
                    ResultEnum.PARAM_ERROR.getMessage());
        }
        //判断该优惠券是否被领取过
        CardUser result = cardUserRepository.findByCouponIdAndShopIdAndUserId(couponId, shopId, userId);
        if (result != null){
            return ResultResponse.createByErrorCodeMessage(CouponStatusEnum.RECEIVED_COUPON_CARD.getStatus(),
                    CouponStatusEnum.RECEIVED_COUPON_CARD.getMessage());
        }
        //领取数量+1
        couponRepository.updateReceiveCountByCouponId(couponId,shopId);
        //插入card表记录
        CardUser card = new CardUser();
        card.setCount(1);
        card.setCouponId(couponId);
        card.setCreateTime(new Date());
        card.setUpdateTime(new Date());
        card.setStatus(1);
        card.setShopId(shopId);
        card.setUserId(userId);
        cardUserRepository.save(card);

        //插入coupon_user表方便展示优惠券时使用
        CouponUser couponUser = new CouponUser();
        couponUser.setCouponId(couponId);
        couponUser.setCreateTime(new Date());
        couponUser.setReceiveStatus(1);
        couponUser.setShopId(shopId);
        couponUser.setUserId(userId);
        couponUserRepository.save(couponUser);
        return ResultResponse.createBySuccess();
    }


}
