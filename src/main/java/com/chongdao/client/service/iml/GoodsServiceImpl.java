package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Coupon;
import com.chongdao.client.entitys.Good;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.mapper.CategoryMapper;
import com.chongdao.client.mapper.GoodMapper;
import com.chongdao.client.mapper.ShopMapper;
import com.chongdao.client.repository.CouponRepository;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.vo.CouponVO;
import com.chongdao.client.vo.GoodsDetailVo;
import com.chongdao.client.vo.GoodsListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.chongdao.client.common.Const.goodsListOrderBy.*;
import static com.chongdao.client.common.Const.goodsListProActivities.*;

@Service
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 分页查询商品
     * @param keyword 搜索关键词
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @param proActivities 1.满减活动 2.店铺打折 3.店铺红包
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getGoodsByKeyword(String keyword, int pageNum, int pageSize,String categoryId,String  proActivities, String orderBy) {
        //搜索关键词不为空
        if (StringUtils.isNotBlank(keyword)){
            keyword =new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        //排序规则
        if (StringUtils.isNotBlank(orderBy)){
            if (PRICE_ASC_DESC.contains(orderBy) || SALES_ASC_DESC.contains(orderBy)){
                String[] orderByArray = orderBy.split("_");
                //价格排序、销量排序
                orderBy = orderByArray[0] + " " + orderByArray[1];
            }else if (ARRANGEMENT_KEY.contains(orderBy)){
                //综合排序
                orderBy = ARRANGEMENT_VALUE;
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
        //查询所有上架商品(综合排序)
        List<Good> goodList = goodMapper.selectByName(StringUtils.isBlank(keyword) ? null: keyword,
                orderBy,StringUtils.isBlank(categoryId) ? null : categoryId,
                discount,StringUtils.isBlank(proActivities) ? null: proActivities);
        PageInfo pageInfo = new PageInfo(goodList);
        pageInfo.setOrderBy(orderBy);
        pageInfo.setList(goodsListVOList(goodList));
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(),pageInfo);
    }


    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    @Override
    public ResultResponse<GoodsDetailVo> getGoodsDetail(Integer goodsId) {
        if (goodsId == null){
            throw new PetException(ResultEnum.PARAM_ERROR);
        }
        //查询商品
        Good good = goodMapper.selectByPrimaryKey(goodsId);
        if (good == null){
            throw new PetException(ResultEnum.PARAM_ERROR);
        }
        //查询优惠券（属于该商品可以使用或者领取的）
        List<CouponVO> couponVOList = assembleCouponVo(good.getShopId());
        //封装详情VO类
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoodsId(goodsId);
        goodsDetailVo.setCouponVOList(couponVOList);
        BeanUtils.copyProperties(good, goodsDetailVo);
        //计算打折后的价格。折扣必须大于0且不能为空
        if (good.getDiscount() > 0 && good.getDiscount() !=null){
            goodsDetailVo.setDiscountPrice(good.getPrice().multiply(new BigDecimal(good.getDiscount())));
        }
        //查询店铺信息
        Shop shop = shopMapper.selectByPrimaryKey(good.getShopId());
        if (shop == null){
            throw new PetException(ResultEnum.PARAM_ERROR);
        }
        goodsDetailVo.setShopName(shop.getShopName());
        goodsDetailVo.setStartBusinessHours(shop.getStartBusinessHours());
        goodsDetailVo.setEndBusinessHours(shop.getEndBusinessHours());
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(),goodsDetailVo);
    }

    /**
     * 商品详情中的优惠券列表
     * @param shopId 商品id
     * @param type 优惠券类型(1：商品 2: 服务)
     * @return
     */
    @Override
    public ResultResponse<List<CouponVO>> getCouponListByShopIdAndType(Integer shopId, Integer type) {
        List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(shopId, ResultEnum.UP_COUPON.getCode(), type);
        List<CouponVO> couponVOList = Lists.newArrayList();
        couponList.forEach(coupon -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(coupon, couponVO);
            couponVOList.add(couponVO);
        });
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(),couponVOList);
    }


    /**
     * 封装分页查询商品功能
     * @param goodList
     * @return
     */
    private List<GoodsListVO> goodsListVOList(List<Good> goodList){
        List<GoodsListVO> goodsListVOList = Lists.newArrayList();
        goodList.forEach(good -> {
            GoodsListVO goodsListVO = new GoodsListVO();
            BeanUtils.copyProperties(good,goodsListVO);
            //折扣大于0时，才会显示折扣价
            if (good.getDiscount() > 0.0D && good.getDiscount() != null ){
                goodsListVO.setDiscountPrice(good.getPrice().multiply(new BigDecimal(good.getDiscount())));
            }
            //封装优惠券
            goodsListVO.setCouponVOList(assembleCouponVo(good.getShopId()));
            goodsListVOList.add(goodsListVO);
        });
        return goodsListVOList;
    }


    /**
     * 封装优惠券功能方便复用
     * type:0 代表店铺满减活动（不属于优惠券）;1代表优惠券
     * @param shopId
     * @return
     */
    private List<CouponVO> assembleCouponVo(Integer shopId){
        List<CouponVO> couponVOS = Lists.newArrayList();
        //根据店铺查询在架状态的优惠券
        List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(shopId, ResultEnum.UP_COUPON.getCode(), ResultEnum.GOODS.getCode());
        //封装优惠券
        couponList.forEach(coupon -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(coupon,couponVO);
            couponVOS.add(couponVO);
        });
        return couponVOS;
    }
}
