package com.chongdao.client.service.iml;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.CouponCommon;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.AreaRepository;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.service.ShopService;
import com.chongdao.client.utils.DistanceUtil;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.utils.MD5Util;
import com.chongdao.client.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.chongdao.client.common.Const.IP;
import static com.chongdao.client.common.Const.OrderBy.*;
import static com.chongdao.client.common.Const.goodsListProActivities.DISCOUNT;

@Service
@SuppressWarnings("all")
public class ShopServiceImpl extends CommonRepository  implements ShopService {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private CouponCommon couponCommon;


    /**
     * 根据条件展示商店(首页)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> list(Integer userId,String categoryId, String  proActivities, String orderBy ,Double lng,Double lat,String areaCode,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //排序规则
        //排序规则
        if (StringUtils.isNotBlank(orderBy)){
            String[] orderByArray = orderBy.split("_");
            if (SALES_ASC_DESC.contains(orderBy)){
                //销量排序
                if (orderByArray[1].equals(ASC)){
                    orderBy = SALES_ORDER_BY_ASC_AND_DISTANCE_3KM;
                }else {
                    orderBy = SALES_ORDER_BY_DESC_AND_DISTANCE_3KM;
                }

            }else if (FAVORABLE.contains(orderBy)){
                //好评率排序
                orderBy =  FAVORABLE_DESC;
            }else if (ARRANGEMENT_KEY.contains(orderBy)){
                //综合排序
                orderBy =  ARRANGEMENT_VALUE_SHOP;
            }else if (DISTANCE.equals(orderBy)){
                orderBy = DISTANCE;
            } else {
                return null;
            }
        }
        //初始化折扣筛选条件，方便sql拼接
        Integer discount = 0;
        if (StringUtils.isNotBlank(proActivities)){
            //传入的参数内容包含1表示为店铺打折
            String[] strings = proActivities.split(",");
            for (String s : strings) {
                if (DISCOUNT.contains(s)) {
                    discount = 1;
                }
            }
        }
        //查询所有上架店铺(默认综合排序)
        List<Shop> shopList = Lists.newArrayList();
        if (orderBy.equals(ARRANGEMENT_VALUE_SHOP)){
            shopList = shopMapper.selectByArrangementLimit3KM(ARRANGEMENT_VALUE_SHOP, lng, lat, StringUtils.isBlank(categoryId) ? null : categoryId,
                    discount, StringUtils.isBlank(proActivities) ? null : proActivities,areaCode);
        }else {
            shopList = shopMapper.selectByName(
                    orderBy, lng, lat, StringUtils.isBlank(categoryId) ? null : categoryId,
                    discount, StringUtils.isBlank(proActivities) ? null : proActivities,areaCode);
        }
        PageInfo pageInfo = new PageInfo(shopList);
        pageInfo.setList(this.shopListVOList(shopList,userId));
        return ResultResponse.createBySuccess(pageInfo);
    }

    @Override
    public ResultResponse<PageInfo> getShopDataList(Integer managementId, String shopName, Integer pageNum, Integer pageSize) {
        Management management = managementRepository.findById(managementId).orElse(null);
        if(management != null) {
            String areaCode = management.getAreaCode();
            if(StringUtils.isNotBlank(areaCode)) {
                PageHelper.startPage(pageNum,pageSize);
                List<Shop> shops = shopMapper.selectByAreaCodeAndShopName(areaCode, shopName);
                PageInfo pageInfo = new PageInfo(shops);
                pageInfo.setList(shops);
                return ResultResponse.createBySuccess(pageInfo);
            }
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.ERROR.getStatus(), ResultEnum.ERROR.getMessage());
    }

    /**
     * 获取店铺
     * @param shopId
     * @return
     */
    @Override
    public ResultResponse getShopById(Integer shopId,Double lat, Double lng,Integer userId) {
        if (shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        ShopVO shopVO = new ShopVO();
        BeanUtils.copyProperties(shop,shopVO);
        if (!shop.getLogo().contains("http")) {
            shopVO.setLogo(IP + shop.getLogo());
        }
        //获取店铺销量
        Integer salesSum = goodsRepository.findBySalesSum(shopId);
        if (salesSum == null){
            salesSum = 0;
        }
        shopVO.setSales(salesSum);
        //查询该店铺是否已收藏
        int count = favouriteShopRepository.countByUserIdAndShopIdAndStatus(userId, shopId, 1);
        shopVO.setConcernStatus(count);
        //店铺优惠
        shopVO.setCouponInfoList(couponCommon.couponInfoList(shopId,userId == null ? 0 :userId));
        //店铺满减
        shopVO.setCouponInfoFullList(couponCommon.couponInfoFullList(shopId));
        //查询用户到店铺的距离
        double distance = DistanceUtil.getDistance(lat, lng, shop.getLat(), shop.getLng());
        shopVO.setDistance(BigDecimal.valueOf(distance/1000).setScale(1, BigDecimal.ROUND_UP) + "km");
        //商家图片信息
        if (StringUtils.isNotBlank(shop.getShowImg())) {
            String showImgs = "";
            List<String> stringList = Splitter.on(",").trimResults().splitToList(shop.getShowImg());
            if (!CollectionUtils.isEmpty(stringList)) {
                for (String s : stringList) {
                    if (!"http".contains(s)) {
                        showImgs = Joiner.on(",").skipNulls().join(IP + s, showImgs);
                    }
                }
                if (StringUtils.isNotBlank(showImgs)) {
                    shopVO.setShowImg(showImgs);
                }
            }
        }

        return ResultResponse.createBySuccess(shopVO);
    }

    @Override
    @Transactional
    public ResultResponse addShop(Shop shop) {
        Shop s = new Shop();
        BeanUtils.copyProperties(shop, s);
        //根据areaId获取areaCode
        Integer areaId = shop.getAreaId();
        if(areaId != null) {
            Area area = areaRepository.findById(areaId).orElse(null);
            s.setAreaCode(area.getCode());
        }
        if(shop.getId() == null) {
            s.setCreateTime(new Date());
            s.setType(1);
            s.setGrade(0.0);
            s.setStatus(-1);
            Integer i = -1;
            s.setIsHot(i.byteValue());
            s.setIsJoinCommonWeal(i.byteValue());
            s.setIsStop(i.byteValue());
            s.setMoney(new BigDecimal(0));
            //将密码MD5加密
            s.setPassword(MD5Util.MD5(s.getPassword()));
        } else {
            s.setUpdateTime(new Date());
            Shop old = shopRepository.findById(shop.getId()).orElse(null);
            String oldPwd = old.getPassword();
            if(!s.getPassword().equals(oldPwd)) {
                //如果修改了密码, 那么将密码MD5重新加密
                s.setPassword(MD5Util.MD5(s.getPassword()));
            }
        }
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopRepository.saveAndFlush(s));
    }

    /**
     * 获取店铺商品
     * @param shopId
     * @param goodsTypeId
     * @return
     */
    @Override
    public ResultResponse<List<GoodsListVO>> getShopService(Integer shopId, Integer goodsTypeId,Integer userId) {
        List<GoodsTypeVO> goodsTypeVOList = Lists.newArrayList();
        //狗宠物卡片
        List<PetCard> petCardDogs = petCardRepository.findByUserIdAndStatusAndTypeId(userId, 1, 1);
        //猫宠物卡片
        List<PetCard> petCardCats = petCardRepository.findByUserIdAndStatusAndTypeId(userId, 1, 2);
        //获取重量参数
        List<Unit> unitList = unitRepository.findAll();
        GoodsTypeVO goodsTypeVO = new GoodsTypeVO();
        //获取当前类别的商品
        List<GoodsListVO> goodsListVOList = Lists.newArrayList();
        //查询上架商品
        //先判断该分类是否有子级
        List<Good> goodList = Lists.newArrayList();
        List<GoodsType> goodsTypeList = goodsTypeRepository.findByParentIdAndStatus(goodsTypeId, 1);
        List<Integer> goodsTypeIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(goodsTypeList)) {
            goodsTypeList.stream().forEach(goodsType -> {
                goodsTypeIds.add(goodsType.getId());
            });
            goodList = goodsRepository.findByShopIdAndGoodsTypeIdInAndStatus(shopId,goodsTypeIds,(byte)1);

        }else {
            goodList = goodsRepository.findByShopIdAndGoodsTypeIdAndStatus(shopId, goodsTypeId, (byte) 1);
        }
        for (Good good : goodList) {
                GoodsListVO goodsListVO = new GoodsListVO();
                //系数不为0 需提高原价 在进行折扣
                if (good.getRatio() != null && good.getRatio() > 0) {
                    good.setPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getRatio())).setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                BeanUtils.copyProperties(good,goodsListVO);
                if (!good.getIcon().contains("http")) {
                    goodsListVO.setIcon(IP + good.getIcon());
                }
                if (good.getUnitName() != null){
                    goodsListVO.setName(good.getName() + good.getUnitName());
                }
                //去除10，和0一样都是原价
                if (good.getDiscount() == 10.0d) {
                    goodsListVO.setDiscount(0.0d);
                }
                goodsListVO.setDiscountPrice(good.getPrice());
                if (good.getDiscount() != null && good.getDiscount() < 10 && good.getDiscount() > 0.0d) {
                    goodsListVO.setDiscountPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getDiscount()/10)).setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                //宠物卡片
                this.assembelGoodsTypeVO(good, goodsListVO, goodsTypeList,unitList, petCardDogs, petCardCats,userId);
                goodsListVOList.add(goodsListVO);
//                    goodsListVOList.add(goodsListVO);
//                    goodsTypeVO.setGoodsListVOList(goodsListVOList);
//                    goodsTypeVOList.add(goodsTypeVO);
        }
        return ResultResponse.createBySuccess(goodsListVOList);
    }

    /**
     * 宠物卡片匹配服务类
     * @param good
     * @param goodsTypeVO
     * @param unitList
     * @param petCardDogs
     * @param petCardCats
     * @param userId
     * @return
     */
    private GoodsListVO assembelGoodsTypeVO(Good good,GoodsListVO goodsListVO,List<GoodsType> goodsTypeList,List<Unit> unitList,List<PetCard> petCardDogs,List<PetCard> petCardCats,Integer userId){
        List<PetCard> petCardList = Lists.newArrayList();
        if (good.getCategoryId() != 3) {
            if (good.getGoodsTypeId() == 57 || good.getGoodsTypeId() == 59 || good.getGoodsTypeId() == 55 || good.getGoodsTypeId() == 53){
                //获取宠物卡片(狗)
                if (!petCardDogs.isEmpty()) {
                    petCardDogs.stream().forEach(petCard -> {
                        //重量
                        BigDecimal weight = petCard.getWeight();
                        for (Unit unit : unitList) {
                            BigDecimal min = unit.getMin();
                            BigDecimal max = unit.getMax();
                            if (min != null && max != null && weight.compareTo(min) >= 0 && weight.compareTo(max) <= 0) {
                                if (good.getUnitName() != null && unit.getLabel().equals(good.getUnitName())) {
                                    petCard.setId(petCard.getId());
                                    petCard.setGoodsId(good.getId());
                                    petCard.setGoodsName(good.getName() + unit.getLabel());
                                    petCard.setGoodsPrice(good.getPrice());
                                    if (good.getDiscount() == 10.0d || good.getDiscount() == null) {
                                        good.setDiscount(0.0d);
                                    }
                                    if (good.getDiscount() != null && good.getDiscount() < 10 && good.getDiscount() > 0.0d) {
                                        petCard.setDiscountPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getDiscount()/10)).setScale(2,BigDecimal.ROUND_HALF_UP));
                                    }
                                    petCard.setDiscount(good.getDiscount());
                                    //查询实际购买该服务的人数
                                    int paymentNumber = goodMapper.paymentNumber(good.getId());
                                    petCard.setPaymentNumber(paymentNumber);
                                    petCardList.add(petCard);
                                    goodsListVO.setPetCardList(petCardList);
                                }
                            }
                        }
                    });
                }
            } else {
                //获取宠物卡片(猫)
                if (!CollectionUtils.isEmpty(petCardCats)) {
                    petCardCats.stream().forEach(petCard -> {
                        //重量
                        BigDecimal weight = petCard.getWeight();
                        for (Unit unit : unitList) {
                            BigDecimal min = unit.getMin();
                            BigDecimal max = unit.getMax();
                            if (min != null && max != null && weight.compareTo(min) >= 0 && weight.compareTo(max) <= 0) {
                                if (good.getUnitName() != null && unit.getLabel().equals(good.getUnitName())) {
                                    petCard.setGoodsId(good.getId());
                                    petCard.setGoodsName(good.getName() + unit.getLabel());
                                    petCard.setGoodsPrice(good.getPrice());
                                    //查询实际购买该服务的人数
                                    int paymentNumber = goodMapper.paymentNumber(good.getId());
                                    petCard.setPaymentNumber(paymentNumber);
                                    petCardList.add(petCard);
                                    goodsListVO.setPetCardList(petCardList);
                                }
                            }
                        }
                    });
                }
            }
        }
        return goodsListVO;
    }


    /**
     * 获取店铺所有订单评价以及店铺总评价
     * @param shopId
     * @return
     */
    @Override
    public ResultResponse<List<OrderEvalVO>> getShopEvalAll(Integer shopId) {
        if (shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        //获取该店铺的所有评价
        List<OrderEval> orderEvalList = orderEvalMapper.getShopEvalAll(shopId);
        List<OrderEvalVO> orderEvalVOList = Lists.newArrayList();
        List<OrderEvalVO> orderEvalVOS = Lists.newArrayList();
        orderEvalList.forEach(e -> {
            OrderEvalVO orderEvalVO = new OrderEvalVO();
            BeanUtils.copyProperties(e,orderEvalVO);
            orderEvalVO.setShopContent(e.getContent());
            orderEvalVO.setShopImg(e.getImg());
            //获取用户
            User user = userRepository.findById(e.getUserId()).get();
            orderEvalVO.setUserName(user.getName());
            orderEvalVO.setLogo(user.getIcon());
            if (!user.getIcon().contains("http")) {
                orderEvalVO.setLogo(IP + user.getIcon());
            }
            //获取购买商品
            List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderNo(e.getOrderNo());
            if (!CollectionUtils.isEmpty(orderDetailList)){
                orderEvalVO.setGoodsName(orderDetailList.get(0).getName());
            }
            orderEvalVOList.add(orderEvalVO);
        });
        OrderEvalVO orderEvalVO = new OrderEvalVO();
        //获取店铺评分
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        orderEvalVO.setShopGrade(shop.getGrade());
        //获取店铺准时率
        //获取订单完成数目
        Integer countAll = orderInfoMapper.findByShopIdAll(shopId);
        //获取准时完成的订单
        Integer count = orderInfoMapper.findByShopIdPunctuality(shopId);
        BigDecimal punctuality = BigDecimal.valueOf(100.0);
        if (countAll != 0){
            punctuality = BigDecimal.valueOf(count / countAll).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        orderEvalVO.setShopPunctuality(punctuality);
        orderEvalVOS.add(orderEvalVO);
        orderEvalVOList.addAll(orderEvalVOS);
        return ResultResponse.createBySuccess(orderEvalVOList);
    }

    @Override
    public ResultResponse updateShopMoney(Integer shopId, BigDecimal money) {
        Shop shop = shopRepository.findById(shopId).orElse(null);
        BigDecimal oldMoney = shop.getMoney();
        shop.setMoney(oldMoney.add(money));
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopRepository.saveAndFlush(shop));
    }

    /**
     * 搜索店铺
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<List<ShopVO>> pageQuery(String keyword, String areaCode,String categoryId,Integer userId,
                                                  String  proActivities,String orderBy,Double lng,Double lat) {
        //keyword为空查询所有店铺
        if (StringUtils.isBlank(keyword)){
            return ResultResponse.createByErrorMessage("请输入要搜锁的店铺名称");
        }
        if (StringUtils.isNotBlank(orderBy)){
            String[] orderByArray = orderBy.split("_");
            if (SALES_ASC_DESC.contains(orderBy)){
                //销量排序
                if (orderByArray[1].equals(ASC)){
                    orderBy = SALES_ORDER_BY_ASC_AND_SEARCH;
                }else {
                    orderBy = SALES_ORDER_BY_DESC_AND_SEARCH;
                }

            }else if (FAVORABLE.contains(orderBy)){
                //好评率排序
                orderBy =  FAVORABLE_DESC;
            }else if (ARRANGEMENT_KEY.contains(orderBy)){
                //综合排序
                orderBy =  ARRANGEMENT_VALUE_SHOP;
            }else if (DISTANCE.equals(orderBy)){
                orderBy = DISTANCE;
            } else {
                return null;
            }
        }
        //初始化折扣筛选条件，方便sql拼接
        Integer discount = 0;
        if (StringUtils.isNotBlank(proActivities)){
            //传入的参数内容包含1表示为店铺打折
            String[] strings = proActivities.split(",");
            for (String s : strings) {
                if (DISCOUNT.contains(s)) {
                    discount = 1;
                }
            }
        }
        List<Shop> shopList = shopMapper.findShopByConditional(keyword,
                orderBy, lng, lat, StringUtils.isBlank(categoryId) ? null : categoryId,
                discount, StringUtils.isBlank(proActivities) ? null : proActivities,areaCode);
        return ResultResponse.createBySuccess(this.shopListVOList(shopList,null));
    }

    /**
     * 关注店铺/取消关注 1/0
     * @param userId
     * @param shopId
     * @return
     */
    @Override
    public ResultResponse concernShop(Integer userId, Integer shopId) {
        if (shopId == null){
            return  ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),"shopId不能为空!");
        }
        FavouriteShop shop = favouriteShopRepository.findByUserIdAndAndShopId(userId,  shopId);
        if (shop != null){
            //取消关注
            if (shop.getStatus() == 0){
                shop.setStatus(1);
            }else{
                shop.setStatus(0);
            }
            shop.setUpdateTime(new Date());
            favouriteShopRepository.save(shop);
        }else {
            //关注店铺
            FavouriteShop favouriteShop = new FavouriteShop();
            favouriteShop.setStatus(1);
            favouriteShop.setShopId(shopId);
            favouriteShop.setUserId(userId);
            favouriteShop.setUpdateTime(new Date());
            favouriteShop.setCreateTime(new Date());
            favouriteShopRepository.save(favouriteShop);
        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 查看关注店铺列表
     * @param userId
     * @return
     */
    @Override
    public ResultResponse queryConcernShopList(Integer userId,Double lng,Double lat) {
        List<FavouriteShop> favouriteShopList = favouriteShopRepository.findAllByUserIdAndStatus(userId, 1).orElse(null);
        List<Integer> shopIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(favouriteShopList)) {
            favouriteShopList.stream().forEach(favouriteShop -> {
                shopIds.add(favouriteShop.getShopId());
            });
        }else{
            shopIds.add(0);
        }
        List<Shop> shopList = shopMapper.selectConcernShop(shopIds,lng,lat);
        List<ShopVO> shopVOList = this.shopListVOList(shopList,userId);
        return ResultResponse.createBySuccess(shopVOList);
    }

    /**
     * 地图商家数据
     * @param lng
     * @param lat
     * @return
     */
    @Override
    public ResultResponse listGeo(Double lng, Double lat,String areaCode) {
        if (lng == null || lat == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),"经纬度不能为空");
        }
        List<Shop> shopList = shopMapper.listGeo(lng,lat,areaCode);
        //3公里内无店铺
        if (CollectionUtils.isEmpty(shopList)) {
            shopList = shopMapper.listAll(lng,lat,areaCode);
        }
        return ResultResponse.createBySuccess(this.shopListVOList(shopList,null));
    }

    @Override
    public ResultResponse getShopInfo(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        if(tokenVo != null && tokenVo.getUserId() != null) {
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopRepository.findById(tokenVo.getUserId()));
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse getInsranceShopLimit3KM(Double lng, Double lat, String areaCode, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shop> shopList = shopMapper.selectInsuranceShopLimit3KM(lng, lat, areaCode);
        PageInfo pageInfo = new PageInfo(shopList);
        List<ShopVO> shopVOList = Lists.newArrayList();
        shopList.forEach(shop-> {
            ShopVO shopVo = new ShopVO();
            if (!shop.getLogo().contains("http")) {
                shop.setLogo(IP + shop.getLogo());
            }
            BeanUtils.copyProperties(shop, shopVo);
            //封装优惠券
            List<CouponInfo> couponList = couponInfoRepository.findByShopIdInAndCpnState(shop.getId(), CouponStatusEnum.COUPON_PUBLISHED.getStatus());
            shopVo.setCouponInfoList(couponList);
            shopVOList.add(shopVo);
        });
        pageInfo.setList(shopVOList);
        return ResultResponse.createBySuccess(pageInfo);
    }


    private List<ShopVO> shopList(Page<Shop> shopPage){
        List<Shop> shopList = shopPage.getContent();
        List<ShopVO> shopVOList = this.shopListVOList(shopList, null);
        return shopVOList;
    }

    /**
     * 封装分页查询
     * @param shopList
     * @return
     */
    private List<ShopVO> shopListVOList(List<Shop> shopList,Integer userId){
        List<ShopVO> shopVOList = Lists.newArrayList();
        shopList.forEach(shop -> {
            ShopVO shopVO = new ShopVO();
            BeanUtils.copyProperties(shop,shopVO);
            if (!shop.getLogo().contains("http")) {
                shopVO.setLogo(IP + shop.getLogo());
            }
            int count = 0;
            //查询用户购物车数目
            if (userId != null){
                count = cartRepository.countByUserIdAndShopId(userId, shop.getId());
                shopVO.setCheckedCount(count);
            }
            //封装优惠券
            List<CouponInfo> couponList = couponInfoRepository.findByShopIdInAndCpnState(shop.getId(), CouponStatusEnum.COUPON_PUBLISHED.getStatus());
            shopVO.setCouponInfoList(couponList);
            shopVOList.add(shopVO);
        });
        return shopVOList;
    }









}
