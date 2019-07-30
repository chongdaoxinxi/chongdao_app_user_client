package com.chongdao.client.common;

import com.chongdao.client.mapper.*;
import com.chongdao.client.repository.*;
import com.chongdao.client.repository.coupon.*;
import com.chongdao.client.service.*;
import com.chongdao.client.utils.sms.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fenglong
 * @date 2019-06-19 13:34
 */
public class CommonRepository {
    @Autowired
    protected GoodMapper goodMapper;

    @Autowired
    protected CartRepository cartRepository;

    @Autowired
    protected ShopMapper shopMapper;

    @Autowired
    protected CouponRepository couponRepository;

    @Autowired
    protected CardUserRepository cardUserRepository;

    @Autowired
    protected CardRepository cardRepository;

    @Autowired
    protected CartsMapper cartsMapper;

    @Autowired
    protected OrderInfoMapper orderInfoMapper;

    @Autowired
    protected OrderDetailMapper orderDetailMapper;

    @Autowired
    protected UserAddressMapper addressMapper;

    @Autowired
    protected OrderInfoRepository orderInfoRepository;

    @Autowired
    protected OrderRefundRepository orderRefundRepository;

    @Autowired
    protected OrderTranRepository orderTranRepository;

    @Autowired
    protected SmsService smsService;

    @Autowired
    protected ShopRepository shopRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected SMSUtil smsUtil;

    @Autowired
    protected ManagementRepository managementRepository;

    @Autowired
    protected ShopService shopService;

    @Autowired
    protected ShopBillService shopBillService;

    @Autowired
    protected OrderRefundService orderRefundService;

    @Autowired
    protected UserAddressRepository userAddressRepository;

    @Autowired
    protected CouponInfoRepository couponInfoRepository;

    @Autowired
    protected CpnSuperpositionRuleRepository superpositionRuleRepository;

    @Autowired
    protected CpnThresholdRuleRepository thresholdRuleRepository;

    @Autowired
    protected CouponScopeRuleRepository scopeRuleRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected GoodsTypeMapper goodsTypeMapper;

    @Autowired
    protected OrderEvalMapper orderEvalMapper;

    @Autowired
    protected GoodsRepository goodsRepository;

    @Autowired
    protected CpnUserRepository cpnUserRepository;

    @Autowired
    protected GoodsTypeRepository goodsTypeRepository;

    @Autowired
    protected OrderEvalRepository orderEvalRepository;

    @Autowired
    protected OrderExpressEvalRepository orderExpressEvalRepository;

    protected ExpressRepository expressRepository;


    @Autowired
    protected BrandRepository brandRepository;

    @Autowired
    protected ScopeApplicationRepository applicationRepository;

    @Autowired
    protected PetCategoryRepository petCategoryRepository;

    @Autowired
    protected BathingServiceRepository bathingServiceRepository;

    @Autowired
    protected ScopeApplicationRepository scopeApplicationRepository;

    @Autowired
    protected PetCardRepository petCardRepository;

    @Autowired
    protected UnitRepository unitRepository;

    @Autowired
    protected FavouriteShopRepository favouriteShopRepository;

    @Autowired
    protected FavouriteGoodsRepository favouriteGoodsRepository;





}
