package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Coupon;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.CouponRepository;
import com.chongdao.client.service.CouponService;
import com.chongdao.client.vo.CouponVO;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;


    /**
     * 商品详情中的优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(1：商品 2: 服务)
     * @return
     */
    @Override
    public ResultResponse<List<CouponVO>> getCouponListByShopIdAndType(Integer shopId, Integer type) {
        List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(shopId, ResultEnum.UP_COUPON.getStatus(), type);
        List<CouponVO> couponVOList = Lists.newArrayList();
        couponList.forEach(coupon -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(coupon, couponVO);
            couponVOList.add(couponVO);
        });
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(),couponVOList);
    }
}
