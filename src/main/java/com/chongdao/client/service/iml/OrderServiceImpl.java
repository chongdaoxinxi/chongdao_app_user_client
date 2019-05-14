package com.chongdao.client.service.iml;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.*;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.mapper.*;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.OrderService;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.utils.BigDecimalUtil;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.utils.GenerateOrderNo;
import com.chongdao.client.vo.CouponVO;
import com.chongdao.client.vo.OrderCommonVO;
import com.chongdao.client.vo.OrderGoodsVo;
import com.chongdao.client.vo.OrderVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.chongdao.client.common.Const.DUAL;
import static com.chongdao.client.enums.CouponStatusEnum.COUPON_FULL_AC;

@Slf4j
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

    @Autowired
    private CartsMapper cartsMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private UserAddressMapper addressMapper;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderRefundRepository orderRefundRepository;

    @Autowired
    private ShopBillRepository shopBillRepository;

    @Autowired
    private OrderTranRepository orderTranRepository;

    @Autowired
    private ExpressRepository expressRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private ShopRespository shopRespository;

    /**
     * 预下单
     * @param userId
     * @param orderType 1代表预下单 2代表下单
     * @return
     */
    @Override
    public ResultResponse<OrderVo> preOrCreateOrder(Integer userId, OrderCommonVO orderCommonVO, Integer orderType) {
        if (orderCommonVO.getDeliverAddressId() == null){
            return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.ADDRESS_EMPTY.getStatus(), GoodsStatusEnum.ADDRESS_EMPTY.getMessage());
        }
        if (userId == null){
            throw new PetException(ResultEnum.PARAM_ERROR);
        }
        //订单总价
        BigDecimal cartTotalPrice = new BigDecimal(BigInteger.ZERO);
        OrderVo orderVo = new OrderVo();
        Double count = 1.0D;
        //从购物车中获取数据
        List<Carts> cartList = cartsMapper.selectCheckedCartByUserId(userId);
        for (Carts cart : cartList) {
            //查询商品
            Good good = goodMapper.selectByPrimaryKey(cart.getGoodsId());
            //查询店铺
            Shop shop = shopMapper.selectByPrimaryKey(good.getShopId());
            orderVo.setShopName(shop.getShopName());
            if (good != null){
                orderVo.setGoodsName(good.getName());
                orderVo.setGoodsPrice(good.getPrice());
                //用户购买的商品数量
                orderVo.setQuantity(cart.getQuantity());
                //折扣价
                if (good.getDiscount() > 0) {
                    orderVo.setDiscountPrice(good.getPrice().multiply(new BigDecimal(good.getDiscount())));
                    //计算总价(商品存在打折)
                    orderVo.setGoodsTotalPrice(BigDecimalUtil.mul((good.getPrice()).multiply(new BigDecimal(good.getDiscount())).doubleValue(),
                            cart.getQuantity().doubleValue()));
                    count = good.getDiscount();
                }else{
                    //计算总价（无打折）
                    orderVo.setGoodsTotalPrice(BigDecimalUtil.mul(good.getPrice().doubleValue(), cart.getQuantity().doubleValue()));
                }
            }
            orderVo.setAreaCode(shop.getAreaCode());
            orderVo.setUserId(userId);
            orderVo.setShopId(shop.getId());
            //获取符合当前条件商品的满减
            List<CouponVO> couponVOList = this.getCouponVo(shop.getId(), cartTotalPrice);
            orderVo.setCouponList(couponVOList);
            BigDecimal decreasePrice = BigDecimal.ZERO;
            if (!CollectionUtils.isEmpty(couponVOList)) {
                decreasePrice = couponVOList.get(0).getDecreasePrice();
            }
            //总价
            cartTotalPrice = BigDecimalUtil.mul((good.getPrice()).multiply(new BigDecimal(count)).doubleValue(),cart.getQuantity()).add(decreasePrice);
            if (orderCommonVO.getCouponId() != null){
                //TODO 计算使用商品 a 优惠券后的价格

            }
            if (orderCommonVO.getCardId() != null){
                //TODO 计算使用配送优惠券后的价格
            }
        }
        //配送优惠券数量 0:双程 1:单程（商品默认为单程）
        orderVo.setServiceCouponCount(this.getServiceCouponCount(orderVo.getUserId(),orderCommonVO.getServiceType()));
        //商品优惠券数量
        orderVo.setGoodsCouponCount(this.getCouponCount(orderVo.getUserId(),orderVo.getShopId()));
        orderVo.setTotalPrice(cartTotalPrice);
        orderVo.setIsService(orderCommonVO.getIsService());
        orderVo.setServiceType(orderCommonVO.getServiceType());
        orderVo.setPayment(cartTotalPrice);
        //如果orderType为2代表提交订单
        if (orderType == OrderStatusEnum.ORDER_CREATE.getStatus()){
            //创建订单
            this.createOrder(orderVo,orderCommonVO);
        }
        return ResultResponse.createBySuccess(orderVo);


    }

    /**
     * 根据type 获取订单列表
     * @param userId
     * @param type
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getOrderTypeList(Integer userId, String type, int pageNum, int pageSize) {
        if (type == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        PageHelper.startPage(pageNum,pageSize);
        //全部
        if ("all".contains(type)){
            type = "1,2,3,4,5,6,7,8,9";
        }
        List<OrderInfo> orderInfoList = orderInfoMapper.selectByUserIdList(userId, type);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfoList,userId);
        PageInfo pageResult = new PageInfo(orderInfoList);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }


    /**
     * 创建订单
     * @param orderVo
     * @return
     */

    @Transactional
    public ResultResponse<OrderVo> createOrder(OrderVo orderVo,OrderCommonVO orderCommonVO) {

        //下单类型为服务类型订单时，需判断地址
        if (orderCommonVO.getIsService().equals(GoodsStatusEnum.SERVICE.getStatus())) {
            if (orderCommonVO.getReceiveAddressId() == null) {
                return ResultResponse.createByErrorCodeMessage(OrderStatusEnum.ADDRESS_NOT_EMPTY.getStatus(), OrderStatusEnum.ADDRESS_NOT_EMPTY.getMessage());
            }
        }
        //从购物车中获取数据
        List<Carts> cartList = cartsMapper.selectCheckedCartByUserId(orderVo.getUserId());

        //计算这个订单的总价
        ResultResponse serverResponse = this.getCartOrderItem(orderVo.getUserId(),cartList);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<OrderDetail> orderItemList = (List<OrderDetail>)serverResponse.getData();

        //生成订单
        OrderInfo order = this.assembleOrder(orderVo,orderCommonVO);
        if(order == null){
            return ResultResponse.createByErrorMessage("生成订单错误");
        }
        if(CollectionUtils.isEmpty(orderItemList)){
            return ResultResponse.createByErrorMessage("购物车为空");
        }
        for(OrderDetail orderItem : orderItemList){
            orderItem.setOrderNo(order.getOrderNo());
        }
        //mybatis 批量插入
        orderDetailMapper.batchInsert(orderItemList);

        //清空一下购物车
        this.cleanCart(cartList);
        return ResultResponse.createBySuccess();
    }


    /**
     * 封装订单列表
     * @param orderList
     * @param userId
     * @return
     */
    private List<OrderVo> assembleOrderVoList(List<OrderInfo> orderList,Integer userId){
        List<OrderVo> orderVoList = Lists.newArrayList();
        for(OrderInfo order : orderList){
            List<OrderDetail>  orderItemList = Lists.newArrayList();
            if(userId == null){
                //todo 管理员查询的时候 不需要传userId
                orderItemList = orderDetailMapper.getByOrderNo(order.getOrderNo());
            }else{
                orderItemList = orderDetailMapper.getByOrderNoUserId(order.getOrderNo(),userId);
            }
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }


    /**
     * 封装订单详情
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(OrderInfo order,List<OrderDetail> orderItemList){
        OrderVo orderVo = new OrderVo();
        //查询店铺
        Shop shop = shopMapper.selectByPrimaryKey(order.getShopId());
        BeanUtils.copyProperties(order,orderVo);
        orderVo.setShopName(shop.getShopName());
        orderVo.setShopLogo(shop.getLogo());
        //接宠地址
        UserAddress receiveAddress = addressMapper.selectByPrimaryKey(order.getReceiveAddressId());
        //送宠地址
        UserAddress deliverAddress = addressMapper.selectByPrimaryKey(order.getDeliverAddressId());
        if (receiveAddress != null){
            orderVo.setReceiveAddressName(receiveAddress.getLocation() + receiveAddress.getAddress());
        }
        if (deliverAddress != null){
            orderVo.setDeliverAddressName(deliverAddress.getLocation() + deliverAddress.getAddress());
        }
        //订单明细
        List<OrderGoodsVo> orderGoodsVoList = Lists.newArrayList();
        orderItemList.forEach(orderDetail -> {
            OrderGoodsVo orderGoodsVo = new OrderGoodsVo();
            orderGoodsVo.setGoodsId(orderDetail.getId());
            orderGoodsVo.setCreateTime(DateTimeUtil.dateToStr(orderDetail.getCreateTime()));
            orderGoodsVo.setGoodsName(orderDetail.getName());
            orderGoodsVo.setGoodsIcon(orderDetail.getIcon());
            orderGoodsVo.setCurrentPrice(orderDetail.getCurrentPrice());
            orderGoodsVo.setGoodsPrice(orderDetail.getPrice());
            orderGoodsVo.setQuantity(orderDetail.getCount());
            orderGoodsVo.setTotalPrice(orderDetail.getTotalPrice());
            orderGoodsVoList.add(orderGoodsVo);
        });
        orderVo.setOrderGoodsVoList(orderGoodsVoList);
        return orderVo;
    }

    /**
     * 获取购物车信息
     * @param userId
     * @param cartList
     * @return
     */
    private ResultResponse getCartOrderItem(Integer userId,List<Carts> cartList){
        List<OrderDetail> orderItemList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(cartList)){
            return ResultResponse.createByErrorMessage("购物车为空");
        }
        //折扣
        Double count = 1.0D;
        //校验购物车的数据,包括产品的状态和数量
        for(Carts cartItem : cartList){
            OrderDetail orderDetail = new OrderDetail();
            Good good = goodMapper.selectByPrimaryKey(cartItem.getGoodsId());
            if(GoodsStatusEnum.ON_SALE.getStatus() != good.getStatus()){
                return ResultResponse.createByErrorMessage("产品"+good.getName()+"不是在线售卖状态");
            }

            orderDetail.setUserId(userId);
            orderDetail.setGoodId(good.getId());
            orderDetail.setName(good.getName());
            orderDetail.setIcon(good.getIcon());
            orderDetail.setPrice(good.getPrice());
            orderDetail.setCount(cartItem.getQuantity());
            //计算折扣是否为0
            if (good.getDiscount() > 0){
                count = good.getDiscount();
            }
            orderDetail.setCurrentPrice(BigDecimalUtil.mul(good.getPrice().doubleValue(),count));
            orderDetail.setTotalPrice(BigDecimalUtil.mul((good.getPrice()).multiply(new BigDecimal(count)).doubleValue(),cartItem.getQuantity()));
            orderItemList.add(orderDetail);
        }
        return ResultResponse.createBySuccess(orderItemList);
    }



    /**
     * 封装订单生成
     * @return
     */
    private OrderInfo assembleOrder(OrderVo orderVo,OrderCommonVO orderCommonVO){
        OrderInfo order = new OrderInfo();
        String orderNo = GenerateOrderNo.genUniqueKey();
        order.setOrderNo(orderNo);
        order.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        if (orderCommonVO.getPayType() == PaymentTypeEnum.ALI_PAY.getStatus()){
            order.setPaymentType( PaymentTypeEnum.ALI_PAY.getStatus());
        }else{
            order.setPaymentType(PaymentTypeEnum.WX_PAY.getStatus());
        }
        order.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        BeanUtils.copyProperties(orderVo,order);
        BeanUtils.copyProperties(orderCommonVO,orderVo);
        order.setPaymentType(orderCommonVO.getPayType());
        int rowCount = orderInfoMapper.insert(order);
        if(rowCount > 0){
            return order;
        }
        return order;
    }

    /**
     * 清空购物车
     * @param cartList
     */
    private void cleanCart(List<Carts> cartList){
        for(Carts cart : cartList){
            cartsMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    /**
     * 封装符合当前商品满减活动
     * @param shopId
     * @param cartTotalPrice
     * @return
     */
    private List<CouponVO> getCouponVo(Integer shopId, BigDecimal cartTotalPrice){
        List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(shopId, CouponStatusEnum.UP_COUPON.getStatus(), COUPON_FULL_AC.getStatus());
        List<CouponVO> couponVOList = Lists.newArrayList();
        CouponVO couponVO = new CouponVO();
        couponList.forEach(coupon -> {
            if (cartTotalPrice.compareTo(coupon.getFullPrice()) == 1){
                BeanUtils.copyProperties(coupon,couponVO);
                couponVOList.add(couponVO);
            }
        });
        return couponVOList;
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
            servicePrice = card.getDecreasePrice();
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




////////////////////////////////////////////////商家端获取订单////////////////////////////////////////////////////////////////////

    /**
     * 商家端获取订单(全部/待接单/已接单/已完成/退款中)
     * @param shopId
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getShopOrderTypeList(Integer shopId, String type, Integer pageNum, Integer pageSize) {
        if (type == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
        PageHelper.startPage(pageNum,pageSize);
        if ("all".contains(type)){
            type = null;//全部
        } else if(type.equals("1")) {
            type = "1";//待接单
        } else if(type.equals("2")) {
            type = "2,7,10,11,12,13";//已接单
        } else if(type.equals("3")) {
            type = "3,6";//已完成
        } else if(type.equals("4")) {
            type = "0,4,5,8,9";//退款中
        } else {
            type = "";
        }
        List<OrderInfo> orderInfoList = orderInfoMapper.selectByShopIdList(shopId, type);
        List<OrderVo> orderVoList = assembleOrderVoList(orderInfoList, null);
        PageInfo pageResult = new PageInfo(orderInfoList);
        pageResult.setList(orderVoList);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    public ResultResponse refundOrder(Integer orderId) {
        return Optional.ofNullable(orderId).flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> refundOrderData(o, OrderStatusEnum.REFUND_AGREE.getStatus(), OrderStatusEnum.REFUND_PROCESS_ACCEPT.getMessage()))
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 商家退款时, 数据处理
     * @param o
     * @return
     */
    private ResultResponse refundOrderData(OrderInfo o, Integer targetStatus, String note) {
        //更新订单状态为4
        o.setOrderStatus(targetStatus);
        orderInfoRepository.saveAndFlush(o);
        //添加退款记录
        OrderRefund or = new OrderRefund();
        or.setOrderId(o.getId());
        or.setType(2);//商家
        or.setNote(note);
        or.setCreatedate(new Date());
        orderRefundRepository.saveAndFlush(or);
        return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
    }

    /**
     * 商家手动接单
     * @param orderId
     * @return
     */
    @Override
    public ResultResponse acceptOrder(Integer orderId) {
        return Optional.ofNullable(orderId).flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> {
                    o.setOrderStatus(OrderStatusEnum.ACCEPTED_ORDER.getStatus());
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), orderInfoRepository.saveAndFlush(o));
                }).orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    /**
     * 商家接单数据处理
     * @param o
     * @return
     */
    private ResultResponse acceptOrderData(OrderInfo o) {
        //更新状态
        o.setOrderStatus(OrderStatusEnum.ACCEPTED_ORDER.getStatus());
        OrderInfo orderInfo = orderInfoRepository.saveAndFlush(o);
        //计算将要转入商家账户的资金(扣除满减, 折扣, 商家的优惠券)
        BigDecimal realPrice = new BigDecimal(0);
        //将钱转入商家账户, 生成流水记录(shopBill)
        ShopBill sb = new ShopBill();
        sb.setUserId(orderInfo.getUserId());
        sb.setOrderId(orderInfo.getId());
        sb.setPrice(realPrice);
        sb.setNote("用户订单消费");
        sb.setShopId(orderInfo.getShopId());
        sb.setType(1);//订单消费
        sb.setCreatedate(new Date());
        shopBillRepository.saveAndFlush(sb);
        //生成订单资金交易记录(order_tran)
        OrderTran ot = new OrderTran();
        ot.setOrderId(orderInfo.getId());
        ot.setComment("用户消费:" + orderInfo.getPayment() + ", 订单号:" + o.getOrderNo());
        ot.setCreateTime(new Date());
        orderTranRepository.save(ot);
        //发送短信
        String areaCode = "3101";
        List<Express> expressListOp = expressRepository.findByAreaCodeAndStatus(areaCode, 1);
        List<String> phoneList = new ArrayList<>();
        expressListOp.forEach(e -> phoneList.add(e.getPhone()));
        Integer shopId = o.getShopId();
        Shop s = shopRespository.findById(shopId).orElse(null);
        if(s != null) {
            smsService.acceptOrderMsgExpressSender(o.getOrderNo(), s.getShopName(), phoneList);
            smsService.acceptOrderMsgShopSender(o.getOrderNo(), s.getShopName(), s.getPhone());
        }

        return ResultResponse.createBySuccessMessage(ResultEnum.SUCCESS.getMessage());
    }
}
