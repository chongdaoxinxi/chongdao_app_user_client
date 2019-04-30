package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Card;
import com.chongdao.client.entitys.Coupon;
import com.chongdao.client.entitys.Good;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.GoodMapper;
import com.chongdao.client.mapper.ShopMapper;
import com.chongdao.client.repository.CardRepository;
import com.chongdao.client.repository.CardUserRepository;
import com.chongdao.client.repository.CouponRepository;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.utils.BigDecimalUtil;
import com.chongdao.client.vo.CartGoodsVo;
import com.chongdao.client.vo.CartVo;
import com.chongdao.client.vo.CouponVO;
import com.chongdao.client.vo.OrderVo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static com.chongdao.client.common.Const.DUAL;
import static com.chongdao.client.enums.ResultEnum.COUPON_FULL_AC;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CardUserRepository cardUserRepository;

    @Autowired
    private CardRepository cardRepository;



    /**
     * 预下单
     * @param cartGoodsVo
     * @return
     */
    @Override
    public ResultResponse<CartGoodsVo> preOrder(CartGoodsVo cartGoodsVo) {
        //订单总价
        BigDecimal cartTotalPrice = new BigDecimal(BigInteger.ZERO);
        if (cartGoodsVo.getUserId() == null || cartGoodsVo.getGoodsId() == null || cartGoodsVo.getShopId() == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        //查询商品
        Good good = goodMapper.selectByPrimaryKey(cartGoodsVo.getGoodsId());
        //查询店铺
        Shop shop = shopMapper.selectByPrimaryKey(good.getShopId());
        cartGoodsVo.setShopName(shop.getShopName());
        if (good != null){
            cartGoodsVo.setGoodsName(good.getName());
            cartGoodsVo.setGoodsPrice(good.getPrice());
            cartGoodsVo.setGoodsStatus(Integer.valueOf(good.getStatus()));
            //折扣价
            if (good.getDiscount() > 0) {
                cartGoodsVo.setDiscountPrice(good.getPrice().multiply(new BigDecimal(good.getDiscount())));
            }
            //用户购买的商品数量
            cartGoodsVo.setQuantity(cartGoodsVo.getQuantity());
            //计算总价
            cartGoodsVo.setGoodsTotalPrice(BigDecimalUtil.mul(good.getPrice().doubleValue(), cartGoodsVo.getQuantity().doubleValue()));
        }

        //获取符合当前条件商品的满减
        CouponVO couponVo = getCouponVo(cartGoodsVo.getShopId(), cartTotalPrice);
        cartGoodsVo.setCouponVO(couponVo);
        BigDecimal decreasePrice = couponVo.getDecreasePrice() ;
        if (decreasePrice == null){
            decreasePrice = BigDecimal.ZERO;
        }
        //总价
        cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartGoodsVo.getGoodsTotalPrice().doubleValue()).add(decreasePrice);
        //配送优惠券数量 0:双程 1:单程（商品默认为单程）
        cartGoodsVo.setServiceCouponCount(getServiceCouponCount(cartGoodsVo.getUserId(),cartGoodsVo.getOrderType()));
        //商品优惠券数量
        cartGoodsVo.setGoodsCouponCount(getCouponCount(cartGoodsVo.getUserId(),cartGoodsVo.getShopId()));
        cartGoodsVo.setTotalPrice(cartTotalPrice);

        return ResultResponse.createBySuccess(cartGoodsVo);
    }

    /**
     * 创建订单
     * @param orderVo
     * @return
     */
    @Override
    public ResultResponse<OrderVo> createOrder(OrderVo orderVo) {
        return null;
    }


    /**
     * 封装符合当前商品满减活动
     * @param shopId
     * @param cartTotalPrice
     * @return
     */
    private CouponVO getCouponVo(Integer shopId, BigDecimal cartTotalPrice){
        List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(shopId, ResultEnum.UP_COUPON.getStatus(), COUPON_FULL_AC.getStatus());
       // List<CouponVO> couponVOList = Lists.newArrayList();
        CouponVO couponVO = new CouponVO();
        couponList.forEach(coupon -> {
            if (cartTotalPrice.compareTo(coupon.getFullPrice()) == 1){
                BeanUtils.copyProperties(coupon,couponVO);
            }
        });
        return couponVO;
    }


    /**
     * 计算配送费
     * @param cardId
     * @return
     */
    private BigDecimal couputerServiceFee(Integer cardId){
        BigDecimal servicePrice = BigDecimal.ZERO;
        if (cardId != null){
            Card card = cardRepository.findById(cardId).get();
            servicePrice = card.getDesPrice();
        }
        return servicePrice;
    }

    /**
     * 获取所有商品优惠券数量包含官方
     * @param userId
     * @param shopId
     * @return
     */
    private Integer getCouponCount(Integer userId, Integer shopId){
        Integer count = 0;
        //官方免费洗等优惠券数量 + 商家优惠券数量
        count = couponRepository.findByCouponCount(userId,shopId) + getGoodsCouponCount(userId);
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        //查询是否参加公益
        if (shop.getIsJoinCommonWeal() == 1){
            count = count + getCommonShopCount(userId);
        }
        return count;
    }

    /**
     * 获取配送券数量(官方)
     * @param userId
     * @param param 双程：0，单程，1
     * @return
     */
    private Integer getServiceCouponCount(Integer userId, Integer param){
        Integer count = 0;
        if (param == DUAL){
            //双程数量
            count = cardUserRepository.findByUserIdAndRoundTrip(userId);
        }else{
            count = cardUserRepository.findByUserIdAndSingleTrip(userId);
        }
        return  count;
    }

    /**
     * 获取商品优惠券数量(官方)包含免费洗等不含公益
     * @param userId
     * @return
     */
    private Integer getGoodsCouponCount(Integer userId){
        Integer count = cardUserRepository.findByUserIdAndShopIdsIsNull(userId);
        return count == null ? 0 : count;
    }

    /**
     * 获取公益优惠券数量
     * @param userId
     * @return
     */
    private Integer getCommonShopCount(Integer userId){
        Integer count = cardUserRepository.findByUserIdCommon(userId);
        return count == null ? 0 : count;
    }





}
