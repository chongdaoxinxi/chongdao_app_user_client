package com.chongdao.client.service.iml;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.Const;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.service.ShopService;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.vo.GoodsListVO;
import com.chongdao.client.vo.GoodsTypeVO;
import com.chongdao.client.vo.OrderEvalVO;
import com.chongdao.client.vo.ShopVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.chongdao.client.common.Const.OrderBy.*;
import static com.chongdao.client.common.Const.goodsListProActivities.DISCOUNT;

@Service
public class ShopServiceImpl extends CommonRepository implements ShopService {
    @Autowired
    private ShopRepository shopRepository;


    /**
     * 根据条件展示商店(首页)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> list(Integer userId,String categoryId, String  proActivities, String orderBy ,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //排序规则
        if (StringUtils.isNotBlank(orderBy)){
            String[] orderByArray = orderBy.split("_");
            if (SALES_ASC_DESC.contains(orderBy)){
                //销量排序
                orderBy = SALES_ORDER_BY+ " " + orderByArray[1];
            }else if (FAVORABLE.contains(orderBy)){
                //好评率排序
                orderBy = orderByArray[0] + " " + orderByArray[1];
            }else if (ARRANGEMENT_KEY.contains(orderBy)){
                //综合排序
                orderBy =  ARRANGEMENT_VALUE_SHOP;
            }else{
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
        //查询所有上架店铺(综合排序)
        List<Shop> shopList = shopMapper.selectByName(
                orderBy,StringUtils.isBlank(categoryId) ? null : categoryId,
                discount,StringUtils.isBlank(proActivities) ? null: proActivities);
        PageInfo pageInfo = new PageInfo(shopList);
        pageInfo.setList(shopListVOList(shopList,userId));
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
                pageInfo.setList(shopListVOList(shops,null));
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
    public ResultResponse getShopById(Integer shopId) {
        if (shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        Shop shop = shopMapper.selectByPrimaryKey(shopId);
        ShopVO shopVO = new ShopVO();
        BeanUtils.copyProperties(shop,shopVO);
        //获取店铺销量
        Integer salesSum = goodsRepository.findBySalesSum(shopId);
        if (salesSum == null){
            salesSum = 0;
        }
        shopVO.setSales(salesSum);
        //封装优惠券(店铺满减除外(cpnType = 4))
        List<CouponInfo> couponList = couponInfoRepository.findByShopIdAndCpnStateAndCpnTypeNot(shop.getId(), CouponStatusEnum.COUPON_PUBLISHED.getStatus(),4);
        List<CouponInfo> couponInfoList = Lists.newArrayList();
        couponList.stream().forEach(e ->{
            //二次校验，过滤失效的优惠券
            long result = DateTimeUtil.costTime(DateTimeUtil.dateToStr(e.getValidityEndDate()),
                    DateTimeUtil.dateToStr(new Date()));
            if (result > 0){
                couponInfoList.add(e);
            }
        });
        shopVO.setCouponInfoList(couponInfoList);
        return ResultResponse.createBySuccess(shopVO);
    }

    public ResultResponse addShop(Shop shop) {
        Shop s = new Shop();
        BeanUtils.copyProperties(shop, s);
        s.setMoney(new BigDecimal(0));
        //根据areaId获取areaCode

        s.setType(1);
        s.setGrade(0.0);
        s.setStatus(-1);
        Integer i = -1;
        s.setIsHot(i.byteValue());
        s.setIsJoinCommonWeal(i.byteValue());
        s.setIsStop(i.byteValue());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopRepository.saveAndFlush(s));
    }

    /**
     * 获取店铺商品
     * @param shopId
     * @param categoryId 0 商品 1 服务
     * @return
     */
    @Override
    public ResultResponse<List<GoodsTypeVO>> getShopService(Integer shopId, Integer categoryId) {
        if (shopId == null || categoryId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }

        List<GoodsTypeVO> goodsTypeVOList = Lists.newArrayList();
        List<Integer> categoryIds = Lists.newArrayList();
        //获取当前店铺的类别如:服务类:洗澡，美容等
        if (categoryId == 0){
            //商品
            categoryIds = Arrays.asList(3,15);
        }else if (categoryId == 1){
            //服务
            List<GoodsType> goodsTypeList = goodsTypeRepository.findByCategoryIdNotInAndStatus(Arrays.asList(3,15), 1);
            List<Integer> ids = Lists.newArrayList();
            goodsTypeList.stream().forEach(goodsType -> {
                ids.add(goodsType.getCategoryId());
            });
            categoryIds = ids;
        }else{
            //筛选条件
            categoryIds = Arrays.asList(categoryId);
        }
        List<GoodsType> goodsTypeList = goodsTypeRepository.findByCategoryIdInAndStatus(categoryIds,1);
        for (GoodsType e : goodsTypeList) {
            GoodsTypeVO goodsTypeVO = new GoodsTypeVO();
            //获取当前类别的商品
            List<GoodsListVO> goodsListVOList = Lists.newArrayList();
            //查询上架商品
            List<Good> goodList = goodsRepository.findByShopIdAndCategoryIdInAndStatus(shopId, categoryIds, (byte) 1);
            for (Good good : goodList) {
                if (e.getId() == good.getGoodsTypeId()){
                    GoodsListVO goodsListVO = new GoodsListVO();
                    goodsTypeVO.setGoodsTypeId(e.getId());
                    goodsTypeVO.setCategoryId(e.getCategoryId());
                    goodsTypeVO.setGoodsTypeName(e.getName());
                    BeanUtils.copyProperties(good,goodsListVO);
                    goodsListVOList.add(goodsListVO);
                    goodsTypeVO.setGoodsListVOList(goodsListVOList);
                }
            }
            if (goodsTypeVO.getCategoryId() != null) {
                goodsTypeVOList.add(goodsTypeVO);
            }
        }
        return ResultResponse.createBySuccess(goodsTypeVOList);
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
            //获取用户
            User user = userRepository.findById(e.getUserId()).get();
            orderEvalVO.setUserName(user.getName());
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
        Double punctuality = 100.0d;
        if (countAll != 0){
            punctuality = Double.valueOf((count / countAll));
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
    public Page<Shop> pageQuery(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        //keyword为空查询所有店铺
        if (StringUtils.isBlank(keyword)){
            Page<Shop> shopPage = shopRepository.findAllByStatusNot(-1,pageable);
            return new PageImpl<Shop>(this.shopList(shopPage),pageable,shopPage.getTotalElements());
        }
        Page<Shop> shopPage = shopRepository.findByShopNameLikeAndStatusNot("%" + keyword + "%", -1, pageable);
        return new PageImpl<Shop>(this.shopList(shopPage),pageable,shopPage.getTotalElements());
    }


    private List<Shop> shopList(Page<Shop> shopPage){
        return shopPage.getContent();
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
            //查询用户购物车数目
            if (userId != null){
                int count = cartRepository.countByUserIdAndShopIdAndChecked(userId, shop.getId(), Const.Cart.CHECKED);
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
