package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.enums.GoodsStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.*;
import com.chongdao.client.repository.CouponRepository;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.service.ShopService;
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
import java.util.List;

import static com.chongdao.client.common.Const.OrderBy.*;
import static com.chongdao.client.common.Const.goodsListProActivities.DISCOUNT;
import static com.chongdao.client.service.iml.GoodsServiceImpl.assembleCouponVo;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Autowired
    private OrderEvalMapper orderEvalMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ShopRepository shopRepository;

    /**
     * 根据条件展示商店(首页)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse<PageInfo> list(String categoryId, String  proActivities, String orderBy ,int pageNum, int pageSize) {
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
        pageInfo.setList(shopListVOList(shopList));
        return ResultResponse.createBySuccess(pageInfo);
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
        //封装优惠券
        List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(shopId, CouponStatusEnum.UP_COUPON.getStatus(), GoodsStatusEnum.GOODS.getStatus());
        shopVO.setCouponVOList(assembleCouponVo(couponList));
        return ResultResponse.createBySuccess(shopVO);
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
        //获取当前店铺的类别如:服务类:洗澡，美容等
        List<GoodsType> goodsTypeList = goodsTypeMapper.selectByCategoryId(shopId, categoryId);
        goodsTypeList.forEach(e -> {
            GoodsTypeVO goodsTypeVO = new GoodsTypeVO();
            //获取当前类别的商品
            List<GoodsListVO> goodsListVOList = Lists.newArrayList();
            List<Good> goodList = goodMapper.selectByShopIdAndCategoryId(shopId, categoryId);
            for (Good good : goodList) {
                if (e.getId() == good.getGoodTypeId()){
                    GoodsListVO goodsListVO = new GoodsListVO();
                    goodsTypeVO.setGoodsTypeId(e.getId());
                    goodsTypeVO.setCategoryId(e.getCategoryId());
                    goodsTypeVO.setGoodsTypeName(e.getName());
                    BeanUtils.copyProperties(good,goodsListVO);
                    goodsListVOList.add(goodsListVO);
                    goodsTypeVO.setGoodsListVOList(goodsListVOList);
                }
            }
            goodsTypeVOList.add(goodsTypeVO);
        });
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
    private List<ShopVO> shopListVOList(List<Shop> shopList){
        List<ShopVO> shopVOList = Lists.newArrayList();
        shopList.forEach(shop -> {
            ShopVO shopVO = new ShopVO();
            BeanUtils.copyProperties(shop,shopVO);
            List<Good> goodList = goodMapper.selectListByShopId(shop.getId());
            for (Good good : goodList) {
            //折扣大于0时，才会显示折扣价
            if (good.getDiscount() > 0.0D && good.getDiscount() != null ){
                shopVO.setDiscountPrice(good.getPrice().multiply(new BigDecimal(good.getDiscount())));
                shopVO.setDiscount(good.getDiscount());
            }
            //封装优惠券
            List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(good.getShopId(), CouponStatusEnum.UP_COUPON.getStatus(), GoodsStatusEnum.GOODS.getStatus());
            shopVO.setCouponVOList(assembleCouponVo(couponList));
            }
            shopVOList.add(shopVO);
        });
        return shopVOList;
    }









}
