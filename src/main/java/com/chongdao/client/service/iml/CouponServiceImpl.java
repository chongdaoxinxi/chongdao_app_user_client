package com.chongdao.client.service.iml;

import com.chongdao.client.common.Const;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Card;
import com.chongdao.client.entitys.CardUser;
import com.chongdao.client.entitys.Coupon;
import com.chongdao.client.entitys.CouponUser;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.repository.CardRepository;
import com.chongdao.client.repository.CardUserRepository;
import com.chongdao.client.repository.CouponRepository;
import com.chongdao.client.repository.CouponUserRepository;
import com.chongdao.client.service.CouponService;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.vo.CouponVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CardUserRepository cardUserRepository;

    @Autowired
    private CouponUserRepository couponUserRepository;

    @Autowired
    private CardRepository cardRepository;




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
        couponList.stream().forEach(coupon -> {
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

    /**
     * 查询已领取的优惠券
     * @param userId
     * @param shopId
     * @return
     */
    @Override
    public ResultResponse receiveCouponComplete(Integer userId, Integer shopId) {
        if (userId == null || shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),
                    ResultEnum.PARAM_ERROR.getMessage());
        }
        List<Coupon> couponList = couponRepository.findByUserIdCoupons(userId, shopId);
        return ResultResponse.createBySuccess(couponList);
    }


    /**
     * 查询配送券
     * @param userId
     * @param param
     * @return
     */
    @Override
    public ResultResponse getCardServiceList(Integer userId, String param) {
        if (userId == null  || StringUtils.isBlank(param)){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),
                    ResultEnum.PARAM_ERROR.getMessage());
        }
        //根据param参数判断是双程还是单程，商品默认单程
        if (Const.DUAL.equals(param)){
            //双程
            List<Card> cardList = cardRepository.findByUserIdAndRoundTripList(userId);
            return ResultResponse.createBySuccess(cardList);
        }else{
            //单程
            List<Card> cardList = cardRepository.findByUserIdAndSingleTripList(userId);
            return ResultResponse.createBySuccess(cardList);
        }
    }





    //--------------------------------------------商户端----------------------------------

    /**
     * 添加优惠券
     * @param shopId
     * @param couponVO
     * @param type
     */
    @Override
    public ResultResponse save(Integer shopId, CouponVO couponVO, Integer type) {
        if (shopId == null || couponVO == null || type == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),
                    ResultEnum.PARAM_ERROR.getMessage());
        }
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponVO, coupon);
        coupon.setCreateTime(new Date());
        coupon.setUpdateTime(new Date());
        coupon.setReceiveCouponCount(0);
        coupon.setUsedCouponCount(0);
        if (type == CouponStatusEnum.COUPON_FULL_AC.getStatus()){
            //店铺满减
            coupon.setCouponName("满" + couponVO.getFullPrice().setScale(0) + "元" + "减" + couponVO.getDecreasePrice().setScale(0) + "元");
            coupon.setType(CouponStatusEnum.COUPON_FULL_AC.getStatus());
        }else {
            //优惠券
            coupon.setCouponName("满" + couponVO.getFullPrice().setScale(0) + "元" + "减" + couponVO.getDecreasePrice().setScale(0) + "元");
            coupon.setType(CouponStatusEnum.COUPON_TICKET.getStatus());
        }
        couponRepository.save(coupon);
        computerTimeSub(coupon);
        return ResultResponse.createBySuccess();
    }




    /**
     * 优惠券上架、下架、删除（1，0，-1）
     * @param couponId
     * @return
     */
    @Override
    public ResultResponse updateCouponStatusById(Integer couponId, Integer status) {
        if (couponId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),
                    ResultEnum.PARAM_ERROR.getMessage());
        }
        couponRepository.updateCouponStatusById(couponId, status);
        return ResultResponse.createBySuccess();

    }


    /**
     * 根据shopId查找满减优惠券
     * @param shopId
     * @param type 0 店铺满减 2 优惠券
     * @return
     */
    @Override
    public ResultResponse<List<CouponVO>> findByShopId(Integer shopId, Integer type) {
        List<Coupon> couponList = Lists.newArrayList();
        if (type == 1){
            //店铺满减
            couponList = couponRepository.findByShopIdAndTypeInAndStatusNotOrderByCreateTimeDesc(shopId, Arrays.asList(0),CouponStatusEnum.COUPON_FULL_AC.getStatus());
        }else{
            //优惠券
            couponList = couponRepository.findByShopIdAndTypeInAndStatusNotOrderByCreateTimeDesc(shopId, Arrays.asList(2),CouponStatusEnum.COUPON_TICKET.getStatus());
        }

        if (CollectionUtils.isEmpty(couponList)){
            return ResultResponse.createBySuccess();
        }
        List<CouponVO> couponVOList = Lists.newArrayList();
        couponList.stream().forEach(coupon -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(coupon,couponVO);
            couponVOList.add(couponVO);
        });
        return ResultResponse.createBySuccess(couponVOList );
    }







    /**
     * 计算优惠券有效时间
     * @param coupon
     */
    private void computerTimeSub(Coupon coupon){
        //计算优惠券有效时间
        //获取当前创建时间
        String curTime = DateTimeUtil.dateToStr(coupon.getCreateTime());
        long time = DateTimeUtil.costTimeDay(coupon.getStartTime(),curTime)*24*60*60;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                computerTime(coupon);
            }
        }, time);


    }

    private void computerTime(Coupon coupon){
        //生效时间
        String activeDate = coupon.getStartTime();
        //失效时间
        String missDate = coupon.getEndTime();
        long time = DateTimeUtil.costTimeDay(missDate,activeDate)*24*60*60 * 1000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //回查
                Coupon result = couponRepository.findById(coupon.getId()).get();
                if (result == null){
                    throw new PetException(ResultEnum.PARAM_ERROR);
                }
                if (result.getStatus().equals(CouponStatusEnum.UP_COUPON.getStatus())){
                    result.setStatus(CouponStatusEnum.DOWN_COUPON.getStatus());
                    couponRepository.save(result);
                }
            }
        }, time);
    }
}
