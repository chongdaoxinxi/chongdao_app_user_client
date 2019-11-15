package com.chongdao.client.service.iml;


import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.CouponCommon;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.dto.OrderGoodsDTO;
import com.chongdao.client.entitys.*;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.entitys.coupon.CpnThresholdRule;
import com.chongdao.client.enums.GoodsStatusEnum;
import com.chongdao.client.enums.OrderStatusEnum;
import com.chongdao.client.enums.PaymentTypeEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.freight.FreightComputer;
import com.chongdao.client.mapper.OrderInfoVOMapper;
import com.chongdao.client.service.*;
import com.chongdao.client.utils.BigDecimalUtil;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.utils.GenerateOrderNo;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.chongdao.client.common.Const.IP;
import static com.chongdao.client.enums.OrderStatusEnum.USER_APPLY_REFUND;
import static com.chongdao.client.utils.DateTimeUtil.STANDARD_FORMAT;

@Slf4j
@Service
public class OrderServiceImpl extends CommonRepository implements OrderService {

    @Autowired
    private CouponServiceImpl couponService;
    @Autowired
    private CartsService cartsService;
    @Autowired
    private FreightComputer freightComputer;
    @Autowired
    private CashAccountService cashAccountService;
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private OrderInfoVOMapper orderInfoVOMapper;
    @Autowired
    private CouponCommon couponCommon;
    @Autowired
    private OrderOperateLogService orderOperateLogService;
//    @Autowired
//    private OrderFeignClient orderFeignClient;

    @Value("${sms.phone}")
    private String phone;

    /**
     * 预下单
     *
     * @param userId orderType 1代表预下单 2代表下单 3拼单
     *               serviceType 服务类型 1.双程 2.单程 3.到店自取
     * @return
     */
    @Override
    public ResultResponse preOrCreateOrder(Integer userId, OrderCommonVO orderCommonVO) {
        if (userId == null) {
            log.error("【预下单】参数不正确, orderCommonVO={} ", orderCommonVO);
            throw new PetException(ResultEnum.PARAM_ERROR);
        }
        //订单总价
        BigDecimal cartTotalPrice = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalDiscount = new BigDecimal(BigInteger.ZERO);
        OrderVo orderVo = new OrderVo();
        //默认地址
//        UserAddress address = userAddressRepository.findByUserIdAndIsDefaultAddress(userId, 1);
//        if (address != null){
//            orderVo.setUserAddress(address);
//        }
        List<Integer> categoryIds = Lists.newArrayList();
        List<Integer> goodsIds = Lists.newArrayList();
        //从购物车中获取数据
        List<Carts> cartList = cartsMapper.selectCheckedCartByUserId(userId, orderCommonVO.getShopId(), null);
        List<OrderGoodsVo> orderGoodsVoList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(cartList)) {
            //订单失效或者已重复支付
            return ResultResponse.createByErrorCodeMessage(OrderStatusEnum.ORDER_VALID.getStatus(), OrderStatusEnum.ORDER_VALID.getMessage());
        }
        //宠物数量
        Integer petCount = 0;
        String petIds = "";
        for (Carts cart : cartList) {
            goodsIds.add(cart.getGoodsId());
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            //用户购买的商品数量
            orderGoodsVo.setShopId(cart.getShopId());
            orderGoodsVo.setGoodsId(cart.getGoodsId());
            orderGoodsVo.setQuantity(cart.getQuantity());
            orderGoodsVoList.add(orderGoodsVo);
            //只有服务才会存在宠物卡片
            if (orderCommonVO.getIsService() == 1) {
                petIds = Joiner.on(",").skipNulls().join(cart.getPetId(), petIds);
            }
            //需要排除一个宠物卡片选择不同类型的服务
            if (petIds.length() == 2) { //长度为2代表购物车遍历的是第一个商品（2, 这种）
                petCount = cart.getPetCount();
            }
            if (!petIds.contains(String.valueOf(cart.getPetId()))) {
                petCount = petCount + cart.getPetCount();
            }
        }
        if (StringUtils.isNotBlank(petIds)) {
            orderVo.setPetIds(petIds.substring(0, petIds.length() - 1));
        }
        orderVo.setPetCount(petCount);
        //订单类型非"到店自取"时(商品除外)，需要判断当前用户是否选择宠物卡片，否则提示用户去选择宠物卡片
        if (orderCommonVO.getServiceType() != 3 && orderCommonVO.getIsService() == 1 && StringUtils.isBlank(petIds) && petCount < 1) {
            return ResultResponse.createByErrorCodeMessage(OrderStatusEnum.PET_CARD_EMPTY.getStatus(), OrderStatusEnum.PET_CARD_EMPTY.getMessage());
        }
        //查询商品
        OrderGoodsDTO orderGoodsDTO = this.orderGoodsDTO(goodsIds, categoryIds, orderGoodsVoList, orderVo, cartTotalPrice);
        //购物车总价
        cartTotalPrice = orderGoodsDTO.getCartTotalPrice();
        //已优惠价格
        totalDiscount = orderVo.getTotalDiscount().add(totalDiscount);
        orderVo.setTotalDiscount(totalDiscount);
        //查询店铺
        Shop shop = shopMapper.selectByPrimaryKey(orderCommonVO.getShopId());
        if (shop != null) {
            orderVo.setShopName(shop.getShopName());
            orderVo.setShopLogo(shop.getLogo());
            if (!shop.getLogo().contains("http")) {
                orderVo.setShopLogo(IP + shop.getLogo());
            }
            orderVo.setOrderGoodsVoList(orderGoodsDTO.getOrderGoodsVoList());
            orderVo.setUserId(userId);
            orderVo.setAreaCode(shop.getAreaCode());
            orderVo.setFollow(orderCommonVO.getFollow());
            orderVo.setShopId(shop.getId());
        }
        //已优惠价格
        orderVo.setTotalDiscount(totalDiscount);
        orderVo.setDiscountPrice(totalDiscount);
        if (orderCommonVO.getCouponId() != null && orderCommonVO.getCouponId() > 0) {
            //计算使用商品优惠券后的价格
            CouponInfo couponInfo = couponInfoRepository.findById(orderCommonVO.getCouponId()).orElse(null);
            if (couponInfo != null) {
                //判断红包与实付款金额大小
                cartTotalPrice = this.cartTotalPrice(cartTotalPrice, couponInfo, orderVo);
            }
        }else {
            //代表无优惠券，可能存在满减（满减不与优惠券共用）
            //匹配符合当前购买条件的满减
            List<CouponInfo> couponInfoFullList = couponCommon.couponInfoFullList(shop.getId());
            this.matchingCouponFull(couponInfoFullList,cartTotalPrice,orderVo);
            //减去满减的价格
            cartTotalPrice = cartTotalPrice.subtract(orderVo.getFullCouponPrice());
        }
        if (orderCommonVO.getCardId() != null && orderCommonVO.getCardId() > 0) {
            //计算使用配送优惠券后的价格
            CouponInfo couponInfo = couponInfoRepository.findById(orderCommonVO.getCardId()).orElse(null);
            if (couponInfo != null) {
                cartTotalPrice = this.cartTotalPrice(cartTotalPrice, couponInfo, orderVo);
            }
        }
        //配送费(到店自取无配送费) 有优惠需减去优惠
        if (orderCommonVO.getServiceType() != 3) {
            BigDecimal originServicePrice = freightComputer.computerFee(
                    orderCommonVO.getServiceType(), orderCommonVO.getIsService(),
                    orderCommonVO.getReceiveAddressId(), orderCommonVO.getDeliverAddressId(),
                    orderVo.getShopId(), userId);
            //原价
            orderVo.setOriginServicePrice(originServicePrice);
            //优惠后的价格
            orderVo.setServicePrice(originServicePrice.subtract(orderVo.getServiceCouponPrice()));
        }
        //测试配送费置为0
        orderVo.setServicePrice(BigDecimal.ZERO);
        //店铺满减
        orderVo.setCouponInfoList(this.getCouponInfoList(orderVo.getShopId()));
        //配送优惠券数量 1:双程 2:单程（商品默认为单程）
        orderVo.setServiceCouponCount(couponService.getExpressCouponCount(userId, orderCommonVO.getServiceType()));
        //商品优惠券数量
        orderVo.setGoodsCouponCount(couponService.countByUserIdAndIsDeleteAndAndCpnType(userId, orderCommonVO.getShopId(), categoryIds, cartTotalPrice));
        //订单总价（包含配送费）
        orderVo.setTotalPrice(cartTotalPrice.add(orderVo.getServicePrice()));
        orderVo.setIsService(orderCommonVO.getIsService());
        orderVo.setServiceType(orderCommonVO.getServiceType());
        //实际付款（包含配送费）
        orderVo.setPayment(cartTotalPrice.add(orderVo.getServicePrice()));
        //如果是配送订单(非到店自取)且宠物数量大于1且用户选择购买了运输险, 那么计算运输险费用并更新OrderVo实体
        Integer serviceType = orderCommonVO.getServiceType();
        Integer isByInsurance = orderCommonVO.getIsByInsurance();
        if (isByInsurance != null && isByInsurance == 1 && serviceType != null && serviceType == 3 && petCount != null && petCount > 1) {
            setInsurancePrice(orderVo);
        }

        //如果orderType为2代表提交订单 3代表拼单
        if (orderCommonVO.getOrderType() == OrderStatusEnum.ORDER_CREATE.getStatus() || orderCommonVO.getOrderType() == OrderStatusEnum.ORDER_SPELL.getStatus()) {
            //地址判断
            if (orderCommonVO.getServiceType() != 3 && (orderCommonVO.getReceiveAddressId() == null || orderCommonVO.getReceiveAddressId() == 0)) {
                if (orderCommonVO.getServiceType() == 1 && orderVo.getDeliverTime() == null && (orderCommonVO.getDeliverAddressId() == null || orderCommonVO.getDeliverAddressId() == 0)) { //双程
                    return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.ADDRESS_EMPTY.getStatus(), GoodsStatusEnum.ADDRESS_EMPTY.getMessage());
                }
                return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.ADDRESS_EMPTY.getStatus(), GoodsStatusEnum.ADDRESS_EMPTY.getMessage());
            } else if ((orderCommonVO.getReceiveAddressId() == null || orderCommonVO.getReceiveAddressId() == 0) || orderCommonVO.getReceiveTime() == null) {
                return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.ADDRESS_EMPTY.getStatus(), GoodsStatusEnum.ADDRESS_EMPTY.getMessage());
            } else {
                //创建订单
                if (orderCommonVO.getCouponId() != null) { //优惠券变为已使用
                    cpnUserRepository.updateUserCpnState(orderCommonVO.getCouponId(),orderVo.getUserId());
                }
                if (orderCommonVO.getCardId() != null) { //配送券变为已使用
                    cpnUserRepository.updateUserCpnState(orderCommonVO.getCardId(),orderVo.getUserId());
                }

                return this.createOrder(orderVo, orderCommonVO);
            }
        }
        return ResultResponse.createBySuccess(orderVo);
    }

    /**
     * 将运输险价格加入
     *
     * @param orderVo
     */
    private void setInsurancePrice(OrderVo orderVo) {
        BigDecimal singlePrice = new BigDecimal(1.5);//单个运输险的价格
        Integer serviceType = orderVo.getServiceType();
        Integer petCount = orderVo.getPetCount();
        BigDecimal totalPrice = orderVo.getTotalPrice();
        BigDecimal payment = orderVo.getPayment();
        BigDecimal insurancePrice = new BigDecimal(0);
        if (serviceType == 2) {
            //单程, 平台承担一只的运输险费用
            insurancePrice = singlePrice.multiply(new BigDecimal(petCount - 1));
        } else if (serviceType == 1) {
            //双程, 平台承担一只的往返运输险费用
            insurancePrice = singlePrice.multiply(new BigDecimal(petCount - 1)).multiply(new BigDecimal(2));
        }
        orderVo.setInsurancePrice(insurancePrice);
        orderVo.setTotalPrice(totalPrice.add(insurancePrice));
        orderVo.setPayment(payment.add(insurancePrice));
    }

    /**
     * 再来一单
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    @Transactional
    public ResultResponse anotherOrder(Integer userId, String orderNo, Integer shopId) {
        return cartsService.anotherAdd(userId, orderNo, shopId);
    }


    /**
     * 追加订单
     *
     * @param userId
     * @param shopId
     * @param orderType (4)
     * @return
     */
    @Transactional
    @Override
    public ResultResponse reAddOrder(Integer userId, String orderNo, Integer shopId, Integer orderType, BigDecimal totalPrice) {
        OrderInfo orderInfo = orderInfoRepository.findByOrderNo(orderNo);
        if (orderInfo == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), "追加订单不存在");
        }
        //可追加条件 参考orderStatusEnum类
        if (!(Arrays.asList(7, 11, 12, 14, 15).contains(orderInfo.getOrderStatus()))) {
            throw new RuntimeException("只有已支付并且服务未完成的订单才可以追加！");
        }
        //查询购物车 生成订单详情
        List<Carts> cartsList = cartsMapper.selectCartByUserId(userId, shopId);
        ResultResponse resultResponse = this.getCartOrderItem(userId, cartsList);
        if (resultResponse.getData() == null) {
            return ResultResponse.createByErrorCodeMessage(OrderStatusEnum.ORDER_VALID.getStatus(), OrderStatusEnum.ORDER_VALID.getMessage());
        }
        List<OrderDetail> orderItemList = (List<OrderDetail>) resultResponse.getData();
        OrderInfoRe orderInfoRe = new OrderInfoRe();
        orderInfoRe.setOrderNo(orderNo);
        orderInfoRe.setReOrderNo("RE" + orderNo);
        orderInfoRe.setUserId(userId);
        orderInfoRe.setCreateTime(new Date());
        orderInfoRe.setPayment(totalPrice);
        orderInfoRe.setShopId(shopId);
        OrderInfoRe o = orderInfoReRepository.save(orderInfoRe);
        for (OrderDetail orderItem : orderItemList) {
            orderItem.setOrderNo(orderNo);
            orderItem.setReOrderNo("RE" + orderNo);
        }
        //mybatis 批量插入
        orderDetailMapper.batchInsert(orderItemList);
        return ResultResponse.createBySuccess(o);
    }

    /**
     * 根据type 获取订单列表
     *
     * @param userId
     * @param type
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getOrderTypeList(Integer userId, String type, int pageNum, int pageSize) {
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        PageHelper.startPage(pageNum, pageSize);
        //全部 （状态含义 参考OrderStatusEnum.class）
        if ("all".contains(type)) {
            type = "-3,-2,-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";
        } else if (type.equals("2")) {//服务中
            type = "7,8,11,12,14,15";
        } else if (type.equals("1")) { //待接单
            type = "1";
        } else if (type.equals("-1")) { //待支付
            type = "-1";
        } else {  //已完成
            type = "-3,-2,0,3,4,5,6,9,10,13";
        }
        List<OrderInfoVO> orderInfoVOList = orderInfoVOMapper.selectByUserIdList(userId, type);
        List<OrderVo> orderVoList = this.assembleOrderInfoVoList(orderInfoVOList, userId);
        PageInfo pageResult = new PageInfo(orderInfoVOList);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 订单详情
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ResultResponse orderDetail(Integer userId, String orderNo) {
        OrderVo orderVo = new OrderVo();
        //获取配送员信息（姓名、电话号码）
        OrderInfo orderInfo = orderInfoRepository.findByOrderNo(orderNo);
        //判断该订单是否属于app，否则为小程序
//        if (orderInfo == null) {
//           return this.orderDetailWxMini(userId, orderNo);
//        }
        orderVo.setOrderNo(orderNo);
        if (orderInfo.getExpressId() != null) {
            Express express = expressRepository.findById(orderInfo.getExpressId()).orElse(null);
            //填充配送员信息
            if (express != null) {
                orderVo.setExpressId(orderInfo.getExpressId());
                orderVo.setExpressName(express.getName());
                orderVo.setExpressPhone(express.getPhone());
                orderVo.setExpressLng(express.getLastLng());
                orderVo.setExpressLat(express.getLastLat());
            }
        }

        //配送费
        orderVo.setServicePrice(orderInfo.getServicePrice());
        //获取店铺名称以及填充订单详情
        Shop shop = shopRepository.findById(orderInfo.getShopId()).get();
        orderVo.setShopId(shop.getId());
        orderVo.setShopName(shop.getShopName());
        orderVo.setShopLogo(shop.getLogo());
        orderVo.setShopPhone(shop.getPhone());
        if (!shop.getLogo().contains("http")) {
            orderVo.setShopLogo(IP + shop.getLogo());
        }
        orderVo.setShopLng(shop.getLng());
        orderVo.setShopLat(shop.getLat());
        orderVo.setShopReceiveTime(orderInfo.getShopReceiveTime());
        orderVo.setShopFinishTime(orderInfo.getShopFinishTime());
        //获取商品详情
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderNo(orderNo);
        List<OrderDetailVO> orderDetailVOS = Lists.newArrayList();
        orderDetailList.stream().forEach(orderDetail -> {
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            orderDetailVO.setGoodsName(orderDetail.getName());
            orderDetailVO.setQuantity(orderDetail.getCount());
            orderDetailVO.setCurrentPrice(orderDetail.getCurrentPrice());
            orderDetailVO.setTotalPrice(orderDetail.getCurrentPrice().multiply(new BigDecimal(orderDetail.getCount())));
            //获取商品折扣
            Good good = goodsRepository.findByIdAndStatus(orderDetail.getGoodId(), (byte) 1);
            orderDetailVO.setGoodsIcon(good.getIcon());
            if (!good.getIcon().contains("http")) {
                orderDetailVO.setGoodsIcon(IP + good.getIcon());
            }
            if (good != null) {
                if ((good.getDiscount() != null && good.getDiscount() < 10 && good.getDiscount() > 0)) {
                    orderVo.setDiscount(good.getDiscount());
                    orderVo.setDiscountPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getDiscount() / 10).setScale(1, BigDecimal.ROUND_HALF_UP)));
                }
                if ((good.getReDiscount() != null && good.getReDiscount() > 0)) {
                    orderVo.setDiscount(good.getReDiscount());
                    orderVo.setDiscountPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getDiscount() / 10).setScale(1, BigDecimal.ROUND_HALF_UP)));
                }
            }
            orderDetailVOS.add(orderDetailVO);
        });
        //优惠券
        if (orderInfo.getCouponId() != null && orderInfo.getCouponId() > 0) {
            CouponInfo couponInfo = couponInfoRepository.findById(orderInfo.getCouponId()).orElse(null);
            if (couponInfo != null) {
                //满减
                if (couponInfo.getCpnType() == 4) {
                    orderVo.setFullCouponName(couponInfo.getCpnName());
                    orderVo.setFullCouponPrice(couponInfo.getCpnValue());
                } else { //红包
                    orderVo.setCouponName(couponInfo.getCpnName());
                    orderVo.setCouponPrice(couponInfo.getCpnValue());
                }
            }
        }
        orderVo.setOrderStatus(orderInfo.getOrderStatus());
        orderVo.setOrderDetailVOList(orderDetailVOS);
        orderVo.setPayment(orderInfo.getGoodsPrice());
        orderVo.setExpressReceiveTime(orderInfo.getExpressReceiveTime());
        orderVo.setExpressFinishTime(orderInfo.getExpressFinishTime());
        orderVo.setReceiveTime(orderInfo.getReceiveTime());
        orderVo.setDeliverTime(orderInfo.getDeliverTime());
        orderVo.setCreateTime(orderInfo.getCreateTime());
        //接宠地址
        if (orderInfo.getReceiveAddressId() != null) {
            UserAddress receiveAddress = userAddressRepository.findByIdAndUserId(orderInfo.getReceiveAddressId(), orderInfo.getUserId());
            if (receiveAddress != null) {
                orderVo.setReceiveAddressName(receiveAddress.getLocation() + receiveAddress.getAddress());
                orderVo.setUserLng(receiveAddress.getLng());
                orderVo.setUserLat(receiveAddress.getLat());
            }

        }
        //送宠地址
        if (orderInfo.getDeliverAddressId() != null) {
            UserAddress deliverAddress = userAddressRepository.findByIdAndUserId(orderInfo.getDeliverAddressId(), orderInfo.getUserId());
            if (deliverAddress != null) {
                orderVo.setDeliverAddressName(deliverAddress.getLocation() + deliverAddress.getAddress());
            }
        }
        if (userId != null) {
            User user = userRepository.findById(orderInfo.getUserId()).orElse(null);
            if (user != null) {
                orderVo.setUsername(user.getName());
                orderVo.setPhone(user.getPhone());
                orderVo.setUserId(userId);
            }
        }
        orderVo.setId(orderInfo.getId());
        orderVo.setServiceType(orderInfo.getServiceType());
        orderVo.setRemark(orderInfo.getRemark());
        return ResultResponse.createBySuccess(orderVo);
    }

    @Override
    public OrderShopVO getOrderDetailByOrderId(Integer orderId) {
        OrderInfo orderInfo = orderInfoRepository.findById(orderId).orElse(null);
        String orderNo = orderInfo.getOrderNo();
        OrderShopVO orderVo = new OrderShopVO();
        BeanUtils.copyProperties(orderInfo, orderVo);
        orderVo.setOrderNo(orderNo);
        //配送费
        orderVo.setServicePrice(orderInfo.getServicePrice());
        //获取店铺名称以及填充订单详情
        Shop shop = shopRepository.findById(orderInfo.getShopId()).get();
        orderVo.setShopId(shop.getId());
        orderVo.setShopName(shop.getShopName());
        orderVo.setShopLogo(shop.getLogo());
        orderVo.setShopPhone(shop.getPhone());
        if (!shop.getLogo().contains("http")) {
            orderVo.setShopLogo(IP + shop.getLogo());
        }
        orderVo.setShopLng(shop.getLng());
        orderVo.setShopLat(shop.getLat());
        orderVo.setShopReceiveTime(orderInfo.getShopReceiveTime());
        orderVo.setShopFinishTime(orderInfo.getShopFinishTime());

        //获取商品详情
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderNo(orderNo);
        List<OrderDetailVO> orderDetailVOS = Lists.newArrayList();
        orderDetailList.stream().forEach(orderDetail -> {
            OrderDetailVO orderDetailVO = new OrderDetailVO();
            orderDetailVO.setGoodsName(orderDetail.getName());
            orderDetailVO.setQuantity(orderDetail.getCount());
            orderDetailVO.setCurrentPrice(orderDetail.getCurrentPrice());
            orderDetailVO.setTotalPrice(orderDetail.getCurrentPrice().multiply(new BigDecimal(orderDetail.getCount())));
            //获取商品折扣
            Good good = goodsRepository.findByIdAndStatus(orderDetail.getGoodId(), (byte) 1);
            orderDetailVO.setGoodsIcon(good.getIcon());
            if (!good.getIcon().contains("http")) {
                orderDetailVO.setGoodsIcon(IP + good.getIcon());
            }
            if (good != null) {
                if ((good.getDiscount() != null && good.getDiscount() < 10 && good.getDiscount() > 0)) {
                    orderVo.setDiscount(good.getDiscount());
                    orderVo.setDiscountPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getDiscount() / 10).setScale(1, BigDecimal.ROUND_HALF_UP)));
                }
                if ((good.getReDiscount() != null && good.getReDiscount() > 0)) {
                    orderVo.setDiscount(good.getReDiscount());
                    orderVo.setDiscountPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getDiscount() / 10).setScale(1, BigDecimal.ROUND_HALF_UP)));
                }
            }
            orderDetailVOS.add(orderDetailVO);
            orderVo.setDiscountTotalPrice(orderVo.getDiscountTotalPrice().add(orderDetail.getPrice().subtract(orderDetail.getCurrentPrice()).multiply(new BigDecimal(orderDetail.getCount()))));
        });

        //优惠券
        if (orderInfo.getCouponId() != null && orderInfo.getCouponId() > 0) {
            CouponInfo couponInfo = couponInfoRepository.findById(orderInfo.getCouponId()).orElse(null);
            if (couponInfo != null) {
                //满减
                if (couponInfo.getCpnType() == 4) {
                    orderVo.setFullCouponName(couponInfo.getCpnName());
                    orderVo.setFullCouponPrice(couponInfo.getCpnValue());
                } else { //红包
                    orderVo.setCouponName(couponInfo.getCpnName());
                    orderVo.setCouponPrice(couponInfo.getCpnValue());
                }
            }
        }
        orderVo.setOrderStatus(orderInfo.getOrderStatus());
        orderVo.setOrderDetailVOList(orderDetailVOS);
        orderVo.setPayment(orderInfo.getGoodsPrice());
        orderVo.setExpressReceiveTime(orderInfo.getExpressReceiveTime());
        orderVo.setExpressFinishTime(orderInfo.getExpressFinishTime());
        orderVo.setReceiveTime(orderInfo.getReceiveTime());
        orderVo.setDeliverTime(orderInfo.getDeliverTime());
        orderVo.setCreateTime(orderInfo.getCreateTime());
        //接宠地址
        if (orderInfo.getReceiveAddressId() != null) {
            UserAddress receiveAddress = userAddressRepository.findByIdAndUserId(orderInfo.getReceiveAddressId(), orderInfo.getUserId());
            if (receiveAddress != null) {
                orderVo.setReceiveAddressName(receiveAddress.getLocation() + receiveAddress.getAddress());
                orderVo.setUserLng(receiveAddress.getLng());
                orderVo.setUserLat(receiveAddress.getLat());
            }

        }
        //送宠地址
        if (orderInfo.getDeliverAddressId() != null) {
            UserAddress deliverAddress = userAddressRepository.findByIdAndUserId(orderInfo.getDeliverAddressId(), orderInfo.getUserId());
            if (deliverAddress != null) {
                orderVo.setDeliverAddressName(deliverAddress.getLocation() + deliverAddress.getAddress());
            }
        }
        Integer userId = orderInfo.getUserId();
        if (userId != null) {
            User user = userRepository.findById(orderInfo.getUserId()).orElse(null);
            if (user != null) {
                orderVo.setUsername(user.getName());
                orderVo.setPhone(user.getPhone());
                orderVo.setUserId(userId);
            }
        }
        orderVo.setId(orderInfo.getId());
        orderVo.setServiceType(orderInfo.getServiceType());
        orderVo.setRemark(orderInfo.getRemark());


        return orderVo;
    }

    /**
     * 小程序订单详情
     * @param userId
     * @param orderNo
     * @return
     */
//    private ResultResponse orderDetailWxMini(Integer userId, String orderNo){
//        //获取订单
//        com.chongdao.client.client.dto.OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
//        if (orderInfo == null) {
//            return null;
//        }
//        OrderVo orderVo = new OrderVo();
//        orderVo.setOrderNo(orderNo);
//        orderVo.setId(orderInfo.getId());
//        if (orderInfo.getExpressId() != null){
//            Express express = orderFeignClient.getExpressById(orderInfo.getExpressId());
//            //填充配送员信息
//            if (express != null) {
//                orderVo.setExpressId(orderInfo.getExpressId());
//                orderVo.setExpressName(express.getName());
//                orderVo.setExpressPhone(express.getPhone());
//            }
//        }
//        //配送费
//        orderVo.setServicePrice(orderInfo.getServicePrice());
//        //获取店铺名称以及填充订单详情
//        com.chongdao.client.client.dto.Shop shop = orderFeignClient.getShop(orderInfo.getShopId());
//        orderVo.setShopId(shop.getId());
//        orderVo.setShopName(shop.getName());
//        orderVo.setShopLogo(shop.getLogo());
//        orderVo.setShopPhone(shop.getTel());
//        orderVo.setShopReceiveTime(null);
//        orderVo.setShopFinishTime(null);
//
//        //获取商品详情
//        List<com.chongdao.client.client.dto.OrderDetail> orderDetailList = orderFeignClient.detail(orderInfo.getId());
//        List<OrderDetailVO> orderDetailVOS = Lists.newArrayList();
//        orderDetailList.stream().forEach(orderDetail -> {
//            OrderDetailVO orderDetailVO = new OrderDetailVO();
//            orderDetailVO.setGoodsName(orderDetail.getGoodsName());
//            orderDetailVO.setQuantity(orderDetail.getCount());
//            orderDetailVO.setCurrentPrice(orderDetail.getPrice());
//            orderDetailVO.setTotalPrice(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getCount())));
//            orderDetailVOS.add(orderDetailVO);
//        });
//        orderVo.setOrderDetailVOList(orderDetailVOS);
//        if (userId != null) {
//            com.chongdao.client.client.dto.User user = orderFeignClient.getUser(userId);
//            if (user != null) {
//                orderVo.setUsername(user.getName());
//                orderVo.setPhone(user.getTel());
//                orderVo.setUserId(userId);
//            }
//        }
//
//        //接宠地址
//        List<com.chongdao.client.client.dto.OrderAddress> addressList = orderFeignClient.getAddress(orderInfo.getId());
//        if (!CollectionUtils.isEmpty(addressList)) {
//            if (addressList.size() > 1) {
//                //双程
//                addressList.stream().forEach(orderAddress -> {
//                    orderVo.setReceiveAddressName(orderAddress.getAddress());
//                    orderVo.setDeliverAddressName(orderAddress.getAddress());
//                });
//
//            } else {
//                addressList.stream().forEach(orderAddress -> {
//                    orderVo.setReceiveAddressName(orderAddress.getAddress());
//                });
//            }
//        }
//        orderVo.setPayment(orderInfo.getGoodsPrice());
//        orderVo.setId(orderInfo.getId());
//        return ResultResponse.createBySuccess(orderVo);
//    }

    /**
     * 获取订单列表PC端
     *
     * @param mgtId
     * @param orderNo
     * @param username
     * @param phone
     * @param orderStatus
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getOrderListPc(Integer mgtId, String orderNo, String username, String phone, String orderStatus, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Management management = managementRepository.findById(mgtId).orElse(null);
        String areaCode = "";
        if (management != null) {
            areaCode = management.getAreaCode();
        }
        List<OrderInfo> orderInfoList = orderInfoMapper.getOrderListPc(areaCode, orderNo, username, phone, orderStatus);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfoList, null);
        PageInfo pageResult = new PageInfo(orderInfoList);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 订单评价
     *
     * @param orderEvalVO
     * @return
     */
    @Override
    @Transactional
    public ResultResponse orderEval(OrderEvalVO orderEvalVO) {
        OrderEval s = orderEvalRepository.findByOrderNo(orderEvalVO.getOrderNo());
        if (s != null) {
            return ResultResponse.createByErrorCodeMessage(4007, "该订单已评价，无需再次评价");
        }
        OrderEval orderEval = new OrderEval();
        //商铺评价
        orderEval.setUserId(orderEvalVO.getUserId());
        orderEval.setContent(orderEvalVO.getShopContent());
        orderEval.setGrade(orderEvalVO.getShopGrade());
        orderEval.setOrderNo(orderEvalVO.getOrderNo());
        orderEval.setShopId(orderEvalVO.getShopId());
        orderEval.setEnabledAnonymous(orderEvalVO.getShopEnabledAnonymous());
        orderEval.setImg(orderEvalVO.getShopImg());
        orderEval.setCreateTime(new Date());
        orderEval.setUpdateTime(new Date());
        orderEvalRepository.save(orderEval);
        //更新店铺评分
        Shop shop = shopRepository.findById(orderEval.getShopId()).get();
        shop.setGrade((orderEval.getGrade() + Double.valueOf(shop.getGrade()) / 2));
        shopRepository.save(shop);

        //配送员评价
        OrderExpressEval orderExpressEval = new OrderExpressEval();
        orderExpressEval.setUserId(orderEvalVO.getUserId());
        orderExpressEval.setContent(orderEvalVO.getExpressContent());
        orderExpressEval.setGrade(orderEvalVO.getExpressGrade());
        orderExpressEval.setOrderNo(orderEvalVO.getOrderNo());
        orderExpressEval.setShopId(orderEvalVO.getShopId());
        orderExpressEval.setEnabledAnonymous(orderEvalVO.getExpressEnabledAnonymous());
        orderExpressEval.setImg(orderEvalVO.getExpressImg());
        orderExpressEval.setCreateTime(new Date());
        orderExpressEval.setUpdateTime(new Date());
        orderExpressEvalRepository.save(orderExpressEval);
        return ResultResponse.createBySuccess();
    }

    /**
     * 评价晒单（初始数据加载）
     *
     * @param orderNo
     * @return
     */
    @Override
    public ResultResponse initOrderEval(String orderNo) {
        OrderInfo orderInfo = orderInfoRepository.findByOrderNo(orderNo);

        OrderEvalVO orderEvalVO = null;
        if (orderInfo != null) {
            Shop shop = shopRepository.findById(orderInfo.getShopId()).get();
            Express express = expressRepository.findById(orderInfo.getExpressId()).orElse(null);
            orderEvalVO = new OrderEvalVO();
            if (shop != null && express != null) {
                orderEvalVO.setShopName(shop.getShopName());
                orderEvalVO.setShopId(shop.getId());
                orderEvalVO.setLogo(shop.getLogo());
                if (!shop.getLogo().contains("http")) {
                    orderEvalVO.setLogo(IP + shop.getLogo());
                }
                orderEvalVO.setExpressName(express.getName());
                orderEvalVO.setExpressId(express.getId());
            }
        }
        return ResultResponse.createBySuccess(orderEvalVO);
    }

    /**
     * 创建订单
     * orderType为4 是追加订单
     *
     * @param orderVo
     * @return
     */

    @Transactional
    public ResultResponse createOrder(OrderVo orderVo, OrderCommonVO orderCommonVO) {
        //从购物车中获取数据
        List<Carts> cartList = cartsMapper.selectCheckedCartByUserId(orderVo.getUserId(), orderCommonVO.getShopId(), null);

        //计算这个订单的总价
        ResultResponse serverResponse = this.getCartOrderItem(orderVo.getUserId(), cartList);
        if (!serverResponse.isSuccess()) {
            return serverResponse;
        }
        List<OrderDetail> orderItemList = (List<OrderDetail>) serverResponse.getData();

        //生成订单
        OrderInfo order = this.assembleOrder(orderVo, orderCommonVO);
        if (order == null) {
            return ResultResponse.createByErrorCodeMessage(OrderStatusEnum.ORDER_CREATE_ERROR.getStatus(),
                    OrderStatusEnum.ORDER_CREATE_ERROR.getMessage());
        }
        if (CollectionUtils.isEmpty(orderItemList)) {
            return ResultResponse.createByErrorCodeMessage(OrderStatusEnum.CART_EMPTY.getStatus(),
                    OrderStatusEnum.CART_EMPTY.getMessage());
        }
        for (OrderDetail orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //mybatis 批量插入
        orderDetailMapper.batchInsert(orderItemList);
        //清空购物车
        this.cleanCart(cartList);
        return ResultResponse.createBySuccess(order);
    }


    /**
     * 封装订单列表
     *
     * @param orderInfoList
     * @param userId
     * @return
     */
    private List<OrderVo> assembleOrderVoList(List<OrderInfo> orderInfoList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        List<OrderDetail> orderItemList = Lists.newArrayList();
        for (OrderInfo order : orderInfoList) {
            if (userId == null) {
                //todo 管理员查询的时候 不需要传userId
                orderItemList = orderDetailMapper.getByOrderNo(order.getOrderNo());
            } else {
                orderItemList = orderDetailMapper.getByOrderNoUserId(order.getOrderNo(), userId);
            }
            OrderVo orderVo = this.assembleOrderInfoVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }


    /**
     * 封装订单列表
     *
     * @param orderInfoVOList
     * @param userId
     * @return
     */
    private List<OrderVo> assembleOrderInfoVoList(List<OrderInfoVO> orderInfoVOList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        List<OrderDetail> orderItemList = Lists.newArrayList();
        for (OrderInfoVO orderInfoVO : orderInfoVOList) {
            if (userId == null) {
                //todo 管理员查询的时候 不需要传userId
                orderItemList = orderDetailMapper.getByOrderNo(orderInfoVO.getOrderNo());
            } else {
                orderItemList = orderDetailMapper.getByOrderNoUserId(orderInfoVO.getOrderNo(), userId);
            }
            OrderVo orderVo = this.assembleOrderVo(orderInfoVO, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    /**
     * 封装订单列表
     *
     * @param orderInfoVO
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(OrderInfoVO orderInfoVO, List<OrderDetail> orderItemList) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(orderInfoVO, orderVo);
        orderVo.setUsername(orderInfoVO.getReceiveUserName());
        orderVo.setPhone(orderInfoVO.getReceiveUserPhone());
        //订单明细
        List<OrderGoodsVo> orderGoodsVoList = Lists.newArrayList();
        //购买商品数目
        Integer goodsCount = 0;
        for (OrderDetail orderDetail : orderItemList) {
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            orderGoodsVo.setGoodsId(orderDetail.getId());
            orderGoodsVo.setCreateTime(DateTimeUtil.dateToStr(orderDetail.getCreateTime()));
            orderGoodsVo.setGoodsName(orderDetail.getName());
            orderGoodsVo.setGoodsIcon(orderDetail.getIcon());
            if (!orderGoodsVo.getGoodsIcon().contains("http")) {
                orderGoodsVo.setGoodsIcon(IP + orderDetail.getIcon());
            }
            orderGoodsVo.setCurrentPrice(orderDetail.getCurrentPrice());
            orderGoodsVo.setGoodsPrice(orderDetail.getPrice());
            orderGoodsVo.setQuantity(orderDetail.getCount());
            orderGoodsVo.setTotalPrice(orderDetail.getTotalPrice());
            goodsCount = goodsCount + orderDetail.getCount();
            orderGoodsVoList.add(orderGoodsVo);
        }
        //设置订单总价
        BigDecimal goodsPrice = orderInfoVO.getGoodsPrice();
        BigDecimal servicePrice = orderInfoVO.getServicePrice();
        if (goodsPrice != null && servicePrice != null) {
            orderVo.setTotalPrice(goodsPrice.add(servicePrice));
        } else {
            if (goodsPrice != null) {
                orderVo.setTotalPrice(goodsPrice);
            } else if (servicePrice != null) {
                orderVo.setTotalPrice(servicePrice);
            }
        }
        orderVo.setGoodsCount(goodsCount);
        orderVo.setOrderGoodsVoList(orderGoodsVoList);
        return orderVo;
    }

    /**
     * 根据购物车信息生成订单详情
     *
     * @param userId
     * @param cartList
     * @return
     */
    private ResultResponse getCartOrderItem(Integer userId, List<Carts> cartList) {
        if (CollectionUtils.isEmpty(cartList)) {
            return ResultResponse.createByErrorMessage("购物车为空");
        }
        List<OrderDetail> orderItemList = Lists.newArrayList();
        //折扣
        Double count = 1.0D;
        //校验购物车的数据,包括产品的状态和数量
        for (Carts cartItem : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            Good good = goodMapper.selectByPrimaryKey(cartItem.getGoodsId());
            if (GoodsStatusEnum.ON_SALE.getStatus() != good.getStatus()) {
                return ResultResponse.createByErrorMessage("产品" + good.getName() + "不是在线售卖状态");
            }

            orderDetail.setUserId(userId);
            orderDetail.setGoodId(good.getId());
            orderDetail.setName(good.getName());
            orderDetail.setIcon(good.getIcon());
            if (!good.getIcon().contains("http")) {
                orderDetail.setIcon(IP + good.getIcon());
            }
            orderDetail.setPrice(good.getPrice());
            orderDetail.setCount(cartItem.getQuantity());
            //计算折扣是否为0
            if (good.getDiscount() > 0 && good.getDiscount() < 10) {
                count = good.getDiscount();
            }
            if (good.getReDiscount() > 0) {
                count = good.getReDiscount();
            }
            orderDetail.setCurrentPrice(BigDecimalUtil.mul(good.getPrice().doubleValue(), count));
            orderDetail.setTotalPrice(BigDecimalUtil.mul((good.getPrice()).multiply(new BigDecimal(count)).doubleValue(), cartItem.getQuantity()));
            orderItemList.add(orderDetail);
        }
        return ResultResponse.createBySuccess(orderItemList);
    }


    /**
     * 封装订单生成
     *
     * @return
     */
    private OrderInfo assembleOrder(OrderVo orderVo, OrderCommonVO orderCommonVO) {
        OrderInfo order = new OrderInfo();
        String orderNo = GenerateOrderNo.genUniqueKey();
        order.setOrderNo(orderNo);
        BeanUtils.copyProperties(orderVo, order);
        order.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        order.setIsService(orderCommonVO.getIsService());
        //等于0 更改为已支付
        if (order.getPayment().compareTo(BigDecimal.ZERO) == 0) {
            order.setOrderStatus(OrderStatusEnum.PAID.getStatus());
        }
        order.setCouponId(orderCommonVO.getCouponId());
        order.setCardId(orderCommonVO.getCardId());
        if (orderVo.getFollow() != null) {
            order.setFollow(Integer.valueOf(orderVo.getFollow()));
        }
        order.setOriginGoodsPrice(order.getOriginGoodsPrice());
        order.setOriginServicePrice(order.getOriginServicePrice());
        order.setPetId(orderVo.getPetIds());
        order.setPetCount(orderVo.getPetCount());
        order.setShopId(orderCommonVO.getShopId());
        order.setGoodsPrice(orderVo.getPayment().subtract(orderVo.getServicePrice()));
        order.setSingleServiceType(orderCommonVO.getSingleServiceType());
        order.setOrderNo(orderNo);
        order.setReceiveAddressId(orderCommonVO.getReceiveAddressId());
        order.setReceiveTime(DateTimeUtil.strToDate(orderCommonVO.getReceiveTime(), STANDARD_FORMAT));
        //双程
        if (orderCommonVO.getServiceType() == 1) {
            order.setDeliverTime(DateTimeUtil.strToDate(orderCommonVO.getDeliverTime(), STANDARD_FORMAT));
            order.setDeliverAddressId(orderCommonVO.getDeliverAddressId());
        }
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setPaymentTime(new Date());
        if (orderCommonVO.getPayType() == PaymentTypeEnum.ALI_PAY.getStatus()) {
            order.setPaymentType(PaymentTypeEnum.ALI_PAY.getStatus());
        } else if (orderCommonVO.getPayType() == PaymentTypeEnum.WX_APP_PAY.getStatus()) {
            order.setPaymentType(PaymentTypeEnum.WX_APP_PAY.getStatus());
        } else if (orderCommonVO.getPayType() == PaymentTypeEnum.WX_XCX_PAY.getStatus()) {
            order.setPaymentType(PaymentTypeEnum.WX_XCX_PAY.getStatus());
        }
        if (orderCommonVO.getOrderType() == OrderStatusEnum.ORDER_SPELL.getStatus()) {
            //拼单
            order.setEnabledSpell(0);
        }
        int rowCount = orderInfoMapper.insert(order);
        if (rowCount > 0) {
            return order;
        }
        return order;
    }


////////////////////////////////////////////////商家端获取订单////////////////////////////////////////////////////////////////////

    /**
     * 商家端获取订单(全部/待接单/已接单/已完成/退款中)
     *
     * @param shopId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getShopOrderTypeList(Integer shopId, String type, Integer pageNum, Integer pageSize) {
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        PageHelper.startPage(pageNum, pageSize);
        if ("all".contains(type)) {
            type = "-3,-2,-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";//全部
        } else if (type.equals("1")) {
            type = "1";//待接单
        } else if (type.equals("2")) {
            type = "2,7,11";//已接单
        } else if (type.equals("3")) {
            type = "3,6,12,14,15";//已完成
        } else if (type.equals("4")) {
            type = "0,4,5,8,9";//退款中
        } else {
            type = "";
        }
        List<OrderInfo> orderInfoList = orderInfoMapper.selectByShopIdList(shopId, type);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfoList, null);
        PageInfo pageResult = new PageInfo(orderInfoList);
        //小程序数据填充
//        List<com.chongdao.client.client.dto.OrderInfo> list = orderFeignClient.list(shopId, type);
//        list.stream().forEach(orderInfo -> {
//            OrderVo orderVo = new OrderVo();
//            List<OrderGoodsVo> orderGoodsVoList = Lists.newArrayList();
//            List<com.chongdao.client.client.dto.OrderDetail> detailList = orderFeignClient.detail(orderInfo.getId());
//            detailList.stream().forEach(orderDetail -> {
//                Shop shop = shopRepository.findById(orderInfo.getShopId()).orElse(null);
//                orderVo.setUserId(orderInfo.getUserId());
//                orderVo.setOrderNo(orderInfo.getOrderNo());
////                orderVo.setReceiveTime(DateTimeUtil.strToDate(orderInfo.getReceiveTime()));
////                orderVo.setDeliverTime(DateTimeUtil.strToTime(orderInfo.getDeliverTime()));
//                orderVo.setPayment(orderInfo.getPayoffamount());
//                orderVo.setShopName(shop.getShopName());
//                orderVo.setOrderStatus(orderInfo.getOrderStatus());
//                OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
//                orderGoodsVo.setGoodsIcon(orderDetail.getIcon());
//                orderGoodsVo.setDiscount(orderDetail.getDiscount());
//                orderGoodsVo.setGoodsName(orderDetail.getGoodsName());
//                orderGoodsVo.setGoodsPrice(orderDetail.getPrice());
//                orderVo.setGoodsCount(orderDetail.getCount());
//                orderGoodsVoList.add(orderGoodsVo);
//                orderVo.setOrderGoodsVoList(orderGoodsVoList);
//                orderVoList.add(orderVo);
//            });
//        });
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    public ResultResponse<PageInfo> getShopOrderTypeListPc(String role, Integer shopId, String orderNo, String username, String phone, String orderStatus, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(role)) {
            //管理员
            if (role.equals("ADMIN_PC")) {
                return getOrderListPc(shopId, orderNo, username, phone, orderStatus, pageNum, pageSize);
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<OrderInfo> orderInfoList = orderInfoMapper.selectByShopIdListPc(shopId, orderNo, username, phone, orderStatus, startDate, endDate);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfoList, null);
        PageInfo pageResult = new PageInfo(orderInfoList);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 商家手动接单
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse shopAcceptOrder(Integer orderId) {
        return Optional.ofNullable(orderId).flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), acceptOrderData(o)))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 商家接单数据处理
     *
     * @param o
     * @return
     */
    private ResultResponse acceptOrderData(OrderInfo o) {
        //更新状态
        o.setOrderStatus(OrderStatusEnum.ACCEPTED_ORDER.getStatus());
        o.setShopReceiveTime(new Date());//接单时间
        OrderInfo orderInfo = orderInfoRepository.saveAndFlush(o);
        //接单资金处理
        cashAccountService.customOrderCashIn(orderInfo);
        //发送短信
        acceptOrderSmsSender(orderInfo);
        //生成流转日志
        orderOperateLogService.addOrderOperateLogService(orderInfo.getId(), orderInfo.getOrderNo(), "", OrderStatusEnum.PAID.getStatus(), OrderStatusEnum.ACCEPTED_ORDER.getStatus());
        return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 商家接单短信通知(->用户+商家+配送员)
     *
     * @param orderInfo
     */
    private void acceptOrderSmsSender(OrderInfo orderInfo) {
        Integer shopId = orderInfo.getShopId();
        if (shopId != null) {
            Shop shop = shopRepository.findById(shopId).orElse(null);
            if (shop != null) {
                List<String> phoneList_express = smsService.getExpressPhoneListByOrderId(orderInfo.getId());
                List<String> phoneList_user = smsService.getUserPhoneListByOrderId(orderInfo.getId());
                if (phoneList_user.size() > 0) {
                    //通知用户
                    smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderAcceptUser(), orderInfo.getOrderNo(), phoneList_user);
                }
                if (StringUtils.isNotBlank(shop.getPhone())) {
                    //通知商家
                    smsService.customOrderMsgSenderSimpleNoShopName(smsUtil.getOrderAcceptShop(), orderInfo.getOrderNo(), shop.getPhone());
                }
                if (phoneList_express.size() > 0) {
                    //通知所有配送员
                    smsService.customOrderMsgSenderPatch(smsUtil.getNewOrderExpress(), shop.getShopName(), orderInfo.getOrderNo(), phoneList_express);
                }
            }
        }
    }

    /**
     * 退款
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse shopRefundOrder(Integer orderId) {
        return Optional.ofNullable(orderId).flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> refundOrderData(o, OrderStatusEnum.REFUND_AGREE.getStatus(), OrderStatusEnum.REFUND_PROCESS_ACCEPT.getMessage(), false))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 管理员确认退款
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse adminConfirmRefund(Integer orderId) {
        return Optional.ofNullable(orderId).flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> adminConfirmRefundData(o))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 管理员确认退款数据处理
     *
     * @return
     */
    private ResultResponse adminConfirmRefundData(OrderInfo orderInfo) {
        orderInfo.setOrderStatus(OrderStatusEnum.REFUND_COMPLETE.getStatus());
        orderInfoRepository.saveAndFlush(orderInfo);
        //添加退款记录
        orderRefundService.addOrderRefundRecord(orderInfo, 4, OrderStatusEnum.REFUND_COMPLETE.getMessage());
        //退款资金处理
        cashAccountService.customOrderCashRefund(orderInfo);
        //短信通知
        adminConfirmRefundSms(orderInfo);
        return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 管理员确认退款短信通知(商家+用户)
     *
     * @return
     */
    private void adminConfirmRefundSms(OrderInfo orderInfo) {
        //TODO
    }

    /**
     * 拒单
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse shopRefuseOrder(Integer orderId) {
        return Optional.ofNullable(orderId).flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> refundOrderData(o, OrderStatusEnum.REFUND_AGREE.getStatus(), OrderStatusEnum.REFUND_PROCESS_REFUSE.getMessage(), true))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 商家退款时, 数据处理
     *
     * @param o
     * @return
     */
    private ResultResponse refundOrderData(OrderInfo o, Integer targetStatus, String refundNote, Boolean isRefuseOrder) {
        //生成流转日志
        orderOperateLogService.addOrderOperateLogService(o.getId(), o.getOrderNo(), "", o.getOrderStatus(), targetStatus);
        //更新订单状态为4
        o.setOrderStatus(targetStatus);
        orderInfoRepository.saveAndFlush(o);
        //添加退款记录
        Integer type = 2;
        if (isRefuseOrder) {
            type = 3;
        }
        orderRefundService.addOrderRefundRecord(o, type, refundNote);
        //发送短信
        //refundOrderSmsSender(o, isRefuseOrder);
        return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 商家同意退款短信通知(同意退款: ->管理员;拒单: ->管理员+用户)
     *
     * @param orderInfo
     */
    private void refundOrderSmsSender(OrderInfo orderInfo, Boolean isRefuseOrder) {
        Integer shopId = orderInfo.getShopId();
        if (shopId != null) {
            Shop shop = shopRepository.findById(shopId).orElse(null);
            if (shop != null) {
                List<String> phoneList_admin = smsService.getAdminPhoneListByOrderId(orderInfo.getId());
                List<String> phoneList_user = smsService.getUserPhoneListByOrderId(orderInfo.getId());
                //通知管理员
                smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderRefundAgreeAdmin(), orderInfo.getOrderNo(), phoneList_admin);
                //通知用户
                if (isRefuseOrder) {
                    //拒单->退款
                    smsService.customOrderMsgSenderPatch(smsUtil.getOrderRefuseUser(), shop.getShopName(), orderInfo.getOrderNo(), phoneList_user);
                } else {
                    //退款
                    smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderRefundAgreeUser(), orderInfo.getOrderNo(), phoneList_user);
                }
            }
        }
    }

    /**
     * 商家服务完成(->用户+配送员)
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public ResultResponse shopServiceCompleted(Integer orderId) {
        return Optional.ofNullable(orderId).flatMap(id -> orderInfoRepository.findById(id))
                .map(o -> shopServiceCompletedData(o))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 商家服务完成数据处理
     *
     * @param orderInfo
     * @return
     */
    private ResultResponse shopServiceCompletedData(OrderInfo orderInfo) {
        //生成流转日志
        orderOperateLogService.addOrderOperateLogService(orderInfo.getId(), orderInfo.getOrderNo(), "", orderInfo.getOrderStatus(), OrderStatusEnum.SHOP_COMPLETE_SERVICE.getStatus());
        //更新状态
        orderInfo.setOrderStatus(OrderStatusEnum.SHOP_COMPLETE_SERVICE.getStatus());
        orderInfo.setShopFinishTime(new Date());//商家服务完成时间
        orderInfoRepository.saveAndFlush(orderInfo);
        //短信通知
        shopServiceCompletedSmsSender(orderInfo);
        return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 商家服务完成短信通知
     *
     * @param orderInfo
     */
    private void shopServiceCompletedSmsSender(OrderInfo orderInfo) {
        //推送短信->所负责的配送员及该订单用户
        Integer shopId = orderInfo.getShopId();
        if (shopId != null) {
            Shop shop = shopRepository.findById(shopId).orElse(null);
            if (shop != null) {
                //通知负责订单的配送员
                String phone = smsService.getExpressPhoneByOrderId(orderInfo.getId());
                if (StringUtils.isNotBlank(phone)) {
                    smsService.customOrderMsgSenderSimple(smsUtil.getOrderShopServiceCompleteExpress(), shop.getShopName(), orderInfo.getOrderNo(), phone);
                }
                //通知用户
                List<String> phoneList = smsService.getUserPhoneListByOrderId(orderInfo.getId());
                if (phoneList.size() > 0) {
                    smsService.customOrderMsgSenderPatchNoShopName(smsUtil.getOrderShopServiceCompleteUser(), orderInfo.getOrderNo(), phoneList);
                }
            }
        }
    }


    ////////////////////////////////////////////////////////配送端//////////////////////////////////////////////////////


    /**
     * 获取普通配送员订单
     *
     * @param expressId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> expressOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize) {
        if (expressId == null || type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        //查出areaCode
        String areaCode = getExpressAreaCode(expressId);
        PageHelper.startPage(pageNum, pageSize);
        if (type.equals("1")) {
            //可接单
            type = "2";
            expressId = null;
        } else if (type.equals("2")) {
            //已接单
            type = "4,5,7,8,9,10,11,12,13,14,15";
        } else if (type.equals("3")) {
            //已完成
            type = "3,6";
        }

        List<OrderInfo> orderInfos = orderInfoMapper.selectExpressOrderList(expressId, type, areaCode);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfos, null);
        PageInfo pageResult = new PageInfo(orderInfos);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 获取管理员配送员订单
     *
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> expressAdminOrderList(Integer expressId, String type, Integer pageNum, Integer pageSize) {
        if (type == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        //查出areaCode
        String areaCode = getExpressAreaCode(expressId);
//        PageHelper.startPage(pageNum, pageSize);
//        if (type.equals("1")) {
//            //商家未接单
//            type = "-1,0,1";
//        } else if (type.equals("2")) {
//            //商家已接单
//            type = "2,3,4,5,6,7,8,9,10,11,12,13,14,15";
//        }
        if (type.equals("1")) {
            //可接单
            type = "2";
        } else if (type.equals("2")) {
            //已接单
            type = "4,5,7,8,9,10,11,12,13,14,15";
        } else if (type.equals("3")) {
            //已完成
            type = "3,6";
        }

        List<OrderInfo> orderInfos = orderInfoMapper.selectExpressAdminOrderList(type, areaCode);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfos, null);
        PageInfo pageResult = new PageInfo(orderInfos);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    public ResultResponse getShopAcceptedOrderStatics(Integer expressId, String type, Integer pageNum, Integer pageSize) {
        if (type.equals("1")) {
            //上门接宠
            type = "7";
        } else if (type.equals("2")) {
            //服务中
            type = "14, 15";
        } else if (type.equals("3")) {
            //到店接宠
            type = "12";
        } else if (type.equals("4")) {
            //已完成
            type = "3, 4, 5, 6, 8, 10, 13";
        } else if (type.equals("5")) {
            type = "1";
        }
        //判断是否管理员
        if (isExpressAdmin(expressId)) {
            expressId = null;
        }
        String areaCode = getExpressAreaCode(expressId);
        List<OrderInfo> orderInfos = orderInfoMapper.selectExpressAdminOrderList(type, areaCode);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfos, null);
        PageInfo pageResult = new PageInfo(orderInfos);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    private String getExpressAreaCode(Integer expressId) {
        String areaCode = "";
        List<Express> list = expressRepository.findByIdAndStatus(expressId, 1);
        if (list.size() > 0) {
            Express express = list.get(0);
            areaCode = express.getAreaCode();
        }
        return areaCode;
    }

    private Boolean isExpressAdmin(Integer expressId) {
        boolean flag = false;
        //判断是否管理员
        List<Express> list = expressRepository.findByIdAndStatus(expressId, 1);
        if (list.size() > 0) {
            Express express = list.get(0);
            Integer expressType = express.getType();
            if (expressType != null && expressType == 2) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public ResultResponse getRefundData(Integer orderId) {
        return orderRefundService.getRefundData(orderId);
    }

    /**
     * 获取使用过优惠券的订单
     *
     * @param token
     * @param orderNo
     * @param username
     * @param phone
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse getConcessionalOrderList(String token, String shopName, String orderNo, String username, String phone, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if (StringUtils.isNotBlank(role)) {
            Integer userId = tokenVo.getUserId();
            if (role.equals("ADMIN_PC")) {
                Management management = managementRepository.findById(userId).orElse(null);
                if (management != null) {
                    return getConcessionalOrderListAdmin(management.getAreaCode(), shopName, orderNo, username, phone, startDate, endDate, pageNum, pageSize);
                }
            } else if (role.equals("SHOP_PC")) {
                return getConcessionalOrderListShop(userId, orderNo, username, phone, startDate, endDate, pageNum, pageSize);
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
    }

    @Override
    public ResultResponse getOrderEvalData(Integer orderId) {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), orderEvalMapper.getOrderEvalData(orderId));
    }

    /**
     * 退款
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    @Transactional
    public ResultResponse refund(Integer userId, String orderNo) {
        if (orderNo == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), "订单号为空");
        }
        OrderInfo orderInfo = orderInfoRepository.findByOrderNo(orderNo);
        if (orderInfo == null) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), "订单不存在");
        }
        orderInfo.setOrderStatus(USER_APPLY_REFUND.getStatus());
        orderInfo.setUpdateTime(new Date());
        orderInfoRepository.save(orderInfo);
        //添加OrderRefund记录
        orderRefundService.addOrderRefundRecord(orderInfo, 1, "用户申请退款");
        //短信推送
        Shop shop = shopRepository.findById(orderInfo.getShopId()).get();
        //推送给商家
        smsService.sendOrderUserRefundShop(orderNo, shop.getPhone());
        //推送管理员
        smsService.sendOrderUserRefundUser(orderInfo.getOrderNo(), shop.getShopName(), phone);
//        //推送配送员
//        if (orderInfo.getExpressId() == null){
//            List<Express> expressList = expressRepository.findByAreaCodeAndStatus(shop.getAreaCode(), 1);
//            expressList.stream().forEach(express -> {
//                smsService.sendOrderUserRefundExpress(orderInfo.getOrderNo(),express.getPhone());
//            });
//        }else{
//            Express express = expressRepository.findById(orderInfo.getExpressId()).get();
//            smsService.sendOrderUserRefundExpress(orderInfo.getOrderNo(),express.getPhone());
//        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 订单详情(商家)
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @Override
    public ResultResponse getShopOrderDetail(Integer userId, String orderNo) {
        return this.orderDetail(userId, orderNo);
    }


    /**
     * 获取使用过优惠券的订单(管理员)
     *
     * @param areaCode
     * @param orderNo
     * @param username
     * @param phone
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    private ResultResponse getConcessionalOrderListAdmin(String areaCode, String shopName, String orderNo, String username, String phone, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderInfo> list = orderInfoMapper.getConcessionalOrderList(areaCode, null, shopName, orderNo, username, phone, startDate, endDate);
        List<OrderVo> orderVoList = assembleOrderVoList(list, null);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 获取使用过优惠券的订单(商店)
     *
     * @param shopId
     * @param orderNo
     * @param username
     * @param phone
     * @param startDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    private ResultResponse getConcessionalOrderListShop(Integer shopId, String orderNo, String username, String phone, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderInfo> list = orderInfoMapper.getConcessionalOrderList(null, shopId, null, orderNo, username, phone, startDate, endDate);
        List<OrderVo> orderVoList = assembleOrderVoList(list, null);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    /**
     * 订单折扣计算方式
     *
     * @param orderGoodsVo
     * @param goodsQuantity
     * @return
     */
    private OrderGoodsVo orderDiscountAndFee(OrderGoodsVo orderGoodsVo, Integer goodsQuantity) {
        //系数不为0 需提高原价 在进行折扣
        if (orderGoodsVo.getRatio() != null && orderGoodsVo.getRatio() > 0) {
            orderGoodsVo.setGoodsPrice(orderGoodsVo.getGoodsPrice().multiply(BigDecimal.valueOf(orderGoodsVo.getRatio())).setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        //折扣价
        if (orderGoodsVo.getDiscount() > 0) {
            orderGoodsVo.setDiscountPrice(BigDecimalUtil.mul(orderGoodsVo.getGoodsPrice().doubleValue(), orderGoodsVo.getDiscount() / 10));
            //计算总价(商品存在打折)
            orderGoodsVo.setGoodsTotalPrice(BigDecimalUtil.mul(orderGoodsVo.getGoodsPrice().doubleValue(), orderGoodsVo.getDiscount() / 10).multiply(new BigDecimal(goodsQuantity)));
            //优惠总计
            orderGoodsVo.setTotalDiscount(orderGoodsVo.getGoodsPrice().multiply(new BigDecimal(goodsQuantity)).subtract(orderGoodsVo.getGoodsTotalPrice()));
        } else {
            //计算总价（无打折）
            orderGoodsVo.setGoodsTotalPrice(orderGoodsVo.getGoodsPrice().multiply(new BigDecimal(goodsQuantity)));
        }
        //第二件打折
        if (orderGoodsVo.getReDiscount() > 0 && goodsQuantity > 1) {
            //商品已打折需要计算在内（然后进行二次打折）
            //第二件打折后的 折扣价格（仅包含第一件 + 第二件 如：第一件价格为：100 第二件价格则为：50 那么reDiscountPrice为：150）
            BigDecimal price = BigDecimalUtil.mul(orderGoodsVo.getGoodsPrice().doubleValue(), orderGoodsVo.getReDiscount() / 10).add(orderGoodsVo.getGoodsPrice());
            orderGoodsVo.setReDiscountPrice(price);
            //商品总价：orderGoodsVo.getReDiscountPrice + 数量 * 原价（此时该商品数量 > 2）
            //记录商品数量
            int quantity = goodsQuantity - 2;
            if (quantity > 0) {
                //购买超过两个
                orderGoodsVo.setGoodsTotalPrice(orderGoodsVo.getReDiscountPrice().add(orderGoodsVo.getGoodsPrice().multiply(new BigDecimal(quantity))));
            } else {
                orderGoodsVo.setGoodsTotalPrice(orderGoodsVo.getReDiscountPrice());
            }
            //优惠总计
            orderGoodsVo.setTotalDiscount((orderGoodsVo.getGoodsPrice().multiply(new BigDecimal(goodsQuantity)).subtract(orderGoodsVo.getGoodsTotalPrice())));
        }
        return orderGoodsVo;
    }

    /**
     * 获取状态为4的店铺满减
     *
     * @param shopId
     * @return
     */
    private List<CpnThresholdRule> getCouponInfoList(Integer shopId) {
        List<CouponInfo> couponInfoList = couponInfoRepository.findByShopIdInAndCpnState(shopId, 1);
        List<Integer> cpnIds = Lists.newArrayList();
        couponInfoList.stream().forEach(couponInfo -> {
            cpnIds.add(couponInfo.getId());
        });
        List<CpnThresholdRule> cpnThresholdRuleList = thresholdRuleRepository.findAllByIdIn(cpnIds);
        return cpnThresholdRuleList;
    }

    /**
     * 商品（服务）封装
     *
     * @param goodsIds
     * @param categoryIds
     * @param orderGoodsVoList
     * @param orderVo
     * @param cartTotalPrice
     * @return
     */
    public OrderGoodsDTO orderGoodsDTO(List<Integer> goodsIds, List<Integer> categoryIds,
                                       List<OrderGoodsVo> orderGoodsVoList, OrderVo orderVo,
                                       BigDecimal cartTotalPrice) {
        OrderGoodsDTO orderGoodsDTO = new OrderGoodsDTO();
        List<Good> goodList = goodsRepository.findAllByIdIn(goodsIds).orElse(null);
        List<OrderGoodsVo> orderGoodsVos = Lists.newArrayList();
        BigDecimal originGoodsPrice = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        BigDecimal totalGoodsPrice = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(goodList)) {
            if (!CollectionUtils.isEmpty(orderGoodsVoList)) {
                for (OrderGoodsVo orderGoodsVo : orderGoodsVoList) {
                    for (Good good : goodList) {
                        categoryIds.add(good.getCategoryId());
                        if (good.getId().equals(orderGoodsVo.getGoodsId()) && good.getShopId().equals(orderGoodsVo.getShopId())) {
                            orderGoodsVo.setGoodsName(good.getName());
                            orderGoodsVo.setGoodsPrice(good.getPrice());
                            if (good.getDiscount() > 0 && good.getDiscount() < 10) {
                                orderVo.setDiscount(good.getDiscount());
                            }
                            if (good.getReDiscount() > 0) {
                                orderVo.setReDiscount(good.getReDiscount());
                            }
                            if (StringUtils.isNotBlank(good.getIcon())) {
                                orderGoodsVo.setGoodsIcon(good.getIcon());
                                if (!good.getIcon().contains("http")) {
                                    orderGoodsVo.setGoodsIcon(IP + good.getIcon());
                                }
                            }
                            if (good.getRatio() != null && good.getRatio() > 0) {
                                orderGoodsVo.setRatio(good.getRatio());
                            }
                            orderGoodsVo.setDiscount(good.getDiscount());
                            orderGoodsVo.setReDiscount(good.getReDiscount());
                            //原价
                            originGoodsPrice = originGoodsPrice.add(good.getPrice().multiply(new BigDecimal(orderGoodsVo.getQuantity())));
                        }
                    }
                    orderVo.setOriginGoodsPrice(originGoodsPrice);
                    //分装categoryId,用于区分优惠券使用场景
                    String result = Joiner.on(",").join(categoryIds);
                    orderVo.setCategoryId(result);
                    //小记
                    totalDiscount = totalDiscount.add(this.orderDiscountAndFee(orderGoodsVo, orderGoodsVo.getQuantity()).getTotalDiscount());
                    orderVo.setTotalDiscount(totalDiscount);
                    //折扣计算包含二次打折
                    totalGoodsPrice = totalGoodsPrice.add(this.orderDiscountAndFee(orderGoodsVo, orderGoodsVo.getQuantity()).getGoodsTotalPrice());
                    orderVo.setGoodsTotalPrice(totalGoodsPrice);
                    orderVo.setGoodsPrice(orderVo.getGoodsTotalPrice());
                    //商品总价（不包含配送费以及优惠券）
                    cartTotalPrice = orderVo.getGoodsTotalPrice();
                    orderGoodsVos.add(orderGoodsVo);
                    orderGoodsDTO.setOrderGoodsVoList(orderGoodsVos);
                }
            }
            orderGoodsDTO.setCartTotalPrice(cartTotalPrice);
        }
        return orderGoodsDTO;
    }


    /**
     * 清空购物车
     *
     * @param cartList
     */
    private void cleanCart(List<Carts> cartList) {
        for (Carts cart : cartList) {
            cartsMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    /**
     * 封装订单信息 2
     *
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderInfoVo(OrderInfo order, List<OrderDetail> orderItemList) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        //查询店铺
        Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
        if (shop != null) {
            orderVo.setShopName(shop.getShopName());
            orderVo.setShopLogo(shop.getLogo());
            if (!shop.getLogo().contains("http")) {
                orderVo.setShopLogo(IP + shop.getLogo());
            }
            orderVo.setShopPhone(shop.getPhone());
        }
        // 接宠地址
        UserAddress receiveAddress = null;
        if (order.getReceiveAddressId() != null) {
            receiveAddress = userAddressRepository.findById(order.getReceiveAddressId()).orElse(null);
            if (receiveAddress != null) {
                orderVo.setReceiveAddressName(receiveAddress.getLocation() + receiveAddress.getAddress());
                orderVo.setPhone(receiveAddress.getPhone());
                orderVo.setUsername(receiveAddress.getUserName());
            }
        }
        //送宠地址
        UserAddress deliverAddress = null;
        if (deliverAddress != null) {
            deliverAddress = userAddressRepository.findById(order.getDeliverAddressId()).orElse(null);
            if (deliverAddress != null) {
                orderVo.setDeliverAddressName(deliverAddress.getLocation() + deliverAddress.getAddress());
                orderVo.setDeliverUserPhone(deliverAddress.getPhone());
                orderVo.setDeliverUserName(deliverAddress.getUserName());
            }
        }

        //订单明细
        List<OrderGoodsVo> orderGoodsVoList = Lists.newArrayList();
        //购买商品数目
        Integer goodsCount = 0;
        for (OrderDetail orderDetail : orderItemList) {
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            orderGoodsVo.setGoodsId(orderDetail.getId());
            orderGoodsVo.setCreateTime(DateTimeUtil.dateToStr(orderDetail.getCreateTime()));
            orderGoodsVo.setGoodsName(orderDetail.getName());
            orderGoodsVo.setGoodsIcon(orderDetail.getIcon());
            if (!orderGoodsVo.getGoodsIcon().contains("http")) {
                orderGoodsVo.setGoodsIcon(IP + orderDetail.getIcon());
            }
            orderGoodsVo.setCurrentPrice(orderDetail.getCurrentPrice());
            orderGoodsVo.setGoodsPrice(orderDetail.getPrice());
            orderGoodsVo.setQuantity(orderDetail.getCount());
            orderGoodsVo.setTotalPrice(orderDetail.getTotalPrice());
            goodsCount = goodsCount + orderDetail.getCount();
            orderGoodsVoList.add(orderGoodsVo);
        }
        //设置订单总价
        BigDecimal goodsPrice = order.getGoodsPrice();
        BigDecimal servicePrice = order.getServicePrice();
        if (goodsPrice != null && servicePrice != null) {
            orderVo.setTotalPrice(goodsPrice.add(servicePrice));
        } else {
            if (goodsPrice != null) {
                orderVo.setTotalPrice(goodsPrice);
            } else if (servicePrice != null) {
                orderVo.setTotalPrice(servicePrice);
            }
        }
        orderVo.setGoodsCount(goodsCount);
        orderVo.setOrderGoodsVoList(orderGoodsVoList);
        return orderVo;
    }


    /**
     * 判断红包与实付款大小
     *
     * @param cartTotalPrice
     * @param couponInfo
     * @param orderVo
     * @return
     */
    private BigDecimal cartTotalPrice(BigDecimal cartTotalPrice, CouponInfo couponInfo, OrderVo orderVo) {
        //判断红包与实付款金额大小
        if (cartTotalPrice.compareTo(couponInfo.getCpnValue()) == -1) {
            orderVo.setTotalDiscount(cartTotalPrice);
            orderVo.setGoodsCouponPrice(cartTotalPrice);
            cartTotalPrice = cartTotalPrice.subtract(cartTotalPrice);
        } else {
            //优惠总计
            orderVo.setTotalDiscount(orderVo.getTotalDiscount().add(couponInfo.getCpnValue()));
            orderVo.setGoodsCouponPrice(couponInfo.getCpnValue());
            cartTotalPrice = cartTotalPrice.subtract(couponInfo.getCpnValue());
        }
        return cartTotalPrice;
    }

    /**
     * 匹配符合当前购买条件的满减（从大到小，默认最先匹配到的满减）
     * @param couponInfoFullList
     * @param cartTotalPrice
     * @param orderVo
     * @return
     */
    private OrderVo matchingCouponFull(List<CouponInfo> couponInfoFullList, BigDecimal cartTotalPrice, OrderVo orderVo) {
        if (!CollectionUtils.isEmpty(couponInfoFullList)) {
            //满减从大到小排序，如果第一个以匹配到，后面无需再次匹配，默认最优
            for (CouponInfo c : couponInfoFullList) {
                if (cartTotalPrice.compareTo(c.getMinPrice()) >= 0) {
                    orderVo.setFullCouponPrice(c.getCpnValue());
                    orderVo.setFullCouponName(c.getCpnName());
                    break;
                }
            }
        }
        return orderVo;
    }

}
