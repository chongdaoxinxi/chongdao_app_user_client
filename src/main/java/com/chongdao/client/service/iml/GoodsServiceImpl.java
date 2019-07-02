package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.CouponStatusEnum;
import com.chongdao.client.enums.GoodsStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.mapper.CategoryMapper;
import com.chongdao.client.mapper.GoodMapper;
import com.chongdao.client.mapper.GoodsTypeMapper;
import com.chongdao.client.mapper.ShopMapper;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.chongdao.client.common.Const.OrderBy.*;
import static com.chongdao.client.common.Const.goodsListProActivities.*;

@Service
public class GoodsServiceImpl implements GoodsService {


    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private  CouponRepository couponRepository;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ScopeApplicationRepository applicationRepository;

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @Autowired
    private BathingServiceRepository bathingServiceRepository;


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
                orderBy = ARRANGEMENT_VALUE_GOODS;
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
        pageInfo.setList(this.goodsListVOList(goodList));
        return ResultResponse.createBySuccess(pageInfo);
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
        List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(good.getShopId(), CouponStatusEnum.UP_COUPON.getStatus(), GoodsStatusEnum.GOODS.getStatus());
        List<CouponVO> couponVOList = this.assembleCouponVo(couponList);
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
            //根据店铺查询在架状态的优惠券
            List<Coupon> couponList = couponRepository.findByShopIdAndStatusAndType(good.getShopId(), CouponStatusEnum.UP_COUPON.getStatus(), GoodsStatusEnum.GOODS.getStatus());
            goodsListVO.setCouponVOList(this.assembleCouponVo(couponList));
            goodsListVOList.add(goodsListVO);
        });
        return goodsListVOList;
    }


    /**
     * 封装优惠券功能方便复用
     * type:0 代表店铺满减活动（不属于优惠券）;1代表优惠券
     * @param couponList
     * @return
     */

    public  static List<CouponVO> assembleCouponVo(List<Coupon> couponList){
        List<CouponVO> couponVOS = Lists.newArrayList();
        //封装优惠券
        couponList.forEach(coupon -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(coupon,couponVO);
            couponVOS.add(couponVO);
        });
        return couponVOS;
    }


    //-------------------------------------------------------------------商户端实现--------------------------------------------------------------------------//


    /**
     * 获取商品类别
     * @return
     */
    @Override
    public ResultResponse getGoodCategoryList(Integer shopId) {
        List<GoodsType> goodCategoryList = goodsTypeMapper.getGoodCategoryList(shopId);
        List<GoodsTypeVO> goodsTypeVOList = Lists.newArrayList();
        goodCategoryList.stream().forEach(goodsType -> {
            GoodsTypeVO goodsTypeVO = new GoodsTypeVO();
            goodsTypeVO.setGoodsTypeName(goodsType.getName());
            goodsTypeVO.setGoodsTypeId(goodsType.getId());
            goodsTypeVO.setCategoryId(goodsType.getCategoryId());
            goodsTypeVO.setSort(goodsType.getSort());
            goodsTypeVO.setStatus(goodsType.getStatus());
            goodsTypeVO.setModuleId(goodsType.getModuleId());
            goodsTypeVOList.add(goodsTypeVO);
        });
        return ResultResponse.createBySuccess(goodsTypeVOList);
    }


    /**
     * 获取商品列表
     * @param goodsTypeId
     * @param goodName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResultResponse getGoodList(Integer shopId,Integer goodsTypeId, String goodName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Good> goodList = goodMapper.getGoodList(shopId,goodsTypeId, goodName);
        List<GoodsListVO> goodsListVOList = Lists.newArrayList();
        goodList.stream().forEach(good -> {
            GoodsListVO goodsListVO = new GoodsListVO();
            BeanUtils.copyProperties(good,goodsListVO);
            goodsListVOList.add(goodsListVO);
        });
        return ResultResponse.createBySuccess(goodsListVOList);
    }

    /**
     * 商品下架
     * @param goodId
     * @param status 1:上架,0下架，-1删除
     * @return
     */
    @Override
    public ResultResponse updateGoodsStatus(Integer goodId, Integer status) {
        if (goodId == null || status == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        goodMapper.updateGoodsStatus(goodId,status);
        return ResultResponse.createBySuccess();
    }


    /**
     * 商品打折
     * @param goodsTypeId
     * @param discount
     * @return
     */
    @Override
    public ResultResponse discountGood(Integer shopId,Integer goodsTypeId, Double discount) {
        if (goodsTypeId == null || discount <= 0 || discount > 9 || shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        goodMapper.goodsDiscount(shopId,goodsTypeId,discount);
        return ResultResponse.createBySuccess();
    }


    /**
     * 获取商品分类
     * @param shopId
     * @return
     */
    @Override
    public ResultResponse categoryList(Integer shopId) {
        if (shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        List<Category> categoryList = categoryRepository.findByStatus(1);
        return ResultResponse.createBySuccess(categoryList);
    }

    /**
     * 增加或编辑商品
     * @param shopId
     * @param goodsListVO
     * @return
     */
    @Override
    public ResultResponse saveOrEditGoods(Integer shopId, GoodsListVO goodsListVO) {
        if (shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        //如果goodsId为null代表增加，否则为编辑
        Good good = new Good();
        BeanUtils.copyProperties(goodsListVO,good);
        if (goodsListVO.getId() == null){
            int result = goodMapper.insert(good);
            if (result == 0){
                return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.SAVE_GOODS_ERROR.getStatus(),
                        GoodsStatusEnum.SAVE_GOODS_ERROR.getMessage());
            }
            return ResultResponse.createBySuccess();
        }
        //编辑
        int result = goodMapper.insertSelective(good);
        if (result == 0){
            return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.SAVE_GOODS_ERROR.getStatus(),
                    GoodsStatusEnum.SAVE_GOODS_ERROR.getMessage());
        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 根据商品id查询
     * @param shopId
     * @param goodsId
     * @return
     */
    @Override
    public ResultResponse selectGoodsById(Integer shopId, Integer goodsId) {
        if (shopId == null || goodsId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        Good good = goodMapper.selectByPrimaryKey(goodsId);
        GoodsListVO goodsListVO = new GoodsListVO();
        BeanUtils.copyProperties(good,goodsListVO);
        PetCategory petCategory = petCategoryRepository.findById(good.getCategoryId()).get();
        goodsListVO.setPetCategoryName(petCategory.getName());
        ScopeApplication application = applicationRepository.findById(good.getScopeId()).get();
        goodsListVO.setScopeName(application.getName());
        Brand brand = brandRepository.findById(good.getBrandId()).get();
        goodsListVO.setBrandName(brand.getName());
        //如果无洗澡服务内容则展示所有
        if (StringUtils.isBlank(good.getBathingServiceId())){
            goodsListVO.setBathingServiceList(bathingServiceRepository.findAll());
        }else{
            goodsListVO.setBathingServiceList(bathingServiceRepository.findByIdIn(good.getBathingServiceId()));
        }
        return ResultResponse.createBySuccess(goodsListVO);
    }


    /**
     * 提高系数
     * @param goodsTypeId
     * @return
     */
    @Override
    public ResultResponse improveRatio(Double ratio,Integer goodsTypeId,Integer shopId) {
        if (goodsTypeId == null || shopId == null || ratio == null || ratio <= 0d || ratio>= 10d){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        //全部
        if (goodsTypeId == 0){
            Integer integer = goodsRepository.updateRatioAndShopId(ratio, shopId);
            if (integer == 0){
                return ResultResponse.createByErrorMessage("提高系数失败");
            }
            return ResultResponse.createBySuccess();
        }
        Integer integer = goodsRepository.updateRatioAndGoodsTypeIdAndShopId(ratio, goodsTypeId, shopId);
        if (integer == 0){
            return ResultResponse.createByErrorMessage("提高系数失败");
        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 一键恢复
     * @param shopId
     * @return
     */
    @Override
    public ResultResponse recoverAll(Integer shopId) {
        if (shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        Integer integer = goodsRepository.updateRatio(shopId);
        if (integer == 0){
            return ResultResponse.createByErrorMessage("一键恢复失败");
        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 启用/禁用/删除 商品类别
     * @param goodTypeId
     * @param status
     * @return
     */
    @Override
    public ResultResponse updateGoodTypeStatus(Integer goodTypeId, Integer status) {
        if (goodTypeId == null || status == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        goodsTypeMapper.updateGoodTypeStatus(goodTypeId,status);
        return ResultResponse.createBySuccess();
    }

    /**
     * 获取所有品牌
     * @return
     */
    @Override
    public ResultResponse<List<Brand>> getBrandList(Integer goodsTypeId) {
//        List<Brand> brandList = brandRepository.findAll();
        List<Brand> brandList = brandRepository.findByGoodsTypeId(goodsTypeId).orElse(null);
        return ResultResponse.createBySuccess(brandList);
    }

    /**
     * 获取宠物试用期以及使用范围分类
     * @param petCategoryId
     * @return
     */
    @Override
    public ResultResponse<List<PetCategoryAndScopeVO>> getPetCategory(Integer categoryId,Integer petCategoryId) {
        List<PetCategory> categoryList = petCategoryRepository.findByCategoryId(categoryId);
        List<PetCategoryAndScopeVO> petCategoryAndScopeVOList = Lists.newArrayList();
        categoryList.forEach(e->{
            //填充宠物分类
            PetCategoryAndScopeVO petCategoryAndScopeVO = new PetCategoryAndScopeVO();
            petCategoryAndScopeVO.setId(e.getId());
            petCategoryAndScopeVO.setPetCategoryName(e.getName());
            if (petCategoryId != null){
                //填充适应期分类
                List<ScopeApplication> scopeApplicationList = applicationRepository.findByPetCategoryId(petCategoryId);
                for (ScopeApplication scopeApplication : scopeApplicationList) {
                    if (scopeApplication.getPetCategoryId() == e.getId()) {
                        petCategoryAndScopeVO.setScopeId(scopeApplication.getId());
                        petCategoryAndScopeVO.setScopeName(scopeApplication.getName());
                    }
                }
            }
            petCategoryAndScopeVOList.add(petCategoryAndScopeVO);
        });
        return ResultResponse.createBySuccess(petCategoryAndScopeVOList);
    }

    /**
     * 获取洗澡服务内容
     * @return
     */
    @Override
    public ResultResponse getBathingService() {
        List<BathingService> bathingServiceList = bathingServiceRepository.findAll();
        return ResultResponse.createBySuccess(bathingServiceList);
    }
}
