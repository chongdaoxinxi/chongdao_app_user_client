package com.chongdao.client.service.iml;

import com.chongdao.client.common.CommonRepository;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.entitys.coupon.CouponInfo;
import com.chongdao.client.entitys.coupon.CpnUser;
import com.chongdao.client.enums.GoodsStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.service.GoodsService;
import com.chongdao.client.vo.GoodsDetailVo;
import com.chongdao.client.vo.GoodsListVO;
import com.chongdao.client.vo.GoodsPcVO;
import com.chongdao.client.vo.ScopeVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.chongdao.client.common.Const.IP;
import static com.chongdao.client.common.Const.OrderBy.*;
import static com.chongdao.client.service.iml.CouponServiceImpl.computerTime;

@Service
public class GoodsServiceImpl extends CommonRepository implements GoodsService {

    /**
     * 分页查询商品
     * @param keyword 搜索关键词
     * @param pageNum 页数
     * @param pageSize 每页数据数量
     * @param orderBy 排序方式(价格、销量、好评等)
     * @return
     */
    @Override
    public ResultResponse<PageInfo> getGoodsByKeyword(String keyword, int pageNum, int pageSize,Integer brandId,Integer goodsTypeId,Integer scopeId,Integer petCategoryId, String areaCode, String orderBy) {
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
        //出现品牌和适用期为条件筛选时需要过滤 "全期" 参数
        String scopeIds = "";
        if (scopeId != null) {
            if (scopeId == 1) {
                scopeIds = "1,2,3,4,5,6";
            }
            if (scopeId == 7) {
                scopeIds = "7,8,9,10,11";
            }
        }
        //查询所有上架商品(综合排序)
        List<Good> goodList = goodMapper.selectByName(StringUtils.isBlank(keyword) ? null: keyword,
                brandId,goodsTypeId,StringUtils.isBlank(scopeIds) ? null: scopeIds,petCategoryId,areaCode,
                orderBy);
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
    public ResultResponse<GoodsDetailVo> getGoodsDetail(Integer goodsId, Integer userId) {
        if (goodsId == null){
            throw new PetException(ResultEnum.PARAM_ERROR);
        }
        //查询商品
        Good good = goodMapper.selectByPrimaryKey(goodsId);
        if (good == null){
            throw new PetException(ResultEnum.PARAM_ERROR);
        }
        //封装详情VO类
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoodsId(goodsId);
        goodsDetailVo.setImg(good.getIcon());
        if (!good.getIcon().contains("http")) {
            goodsDetailVo.setImg(IP + good.getIcon());
        }
        //产品重量
        goodsDetailVo.setUnit(good.getUnit());
        goodsDetailVo.setUnitName(good.getUnitName());
        //产品品牌
        if (good.getBrandId() != null) {
            Brand brand = brandRepository.findById(good.getBrandId()).get();
            goodsDetailVo.setBrandName(brand.getName());
        }
        //适用范围
        if (good.getScopeId() != null) {
            ScopeApplication application = scopeApplicationRepository.findById(good.getScopeId()).get();
            goodsDetailVo.setScopeName(application.getName());
        }
        //categoryId:2 即洗澡类 才会有服务内容
        if (good.getCategoryId() == 2) {
            goodsDetailVo.setServiceContent(bathingServiceRepository.findAll());
        }

        BeanUtils.copyProperties(good, goodsDetailVo);
        //计算打折后的价格。折扣必须大于0且不能为空
        if (good.getDiscount() > 0 && good.getDiscount() !=null){
            goodsDetailVo.setDiscountPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getDiscount()/10).setScale(2,BigDecimal.ROUND_HALF_UP)));
        }
        //商家设置第二件折扣
        if (good.getReDiscount() > 0.0D && good.getReDiscount() != null){
            goodsDetailVo.setReDiscountDesc("第2件" + good.getReDiscount() + "折");
        }
        //查询店铺信息
        Shop shop = shopMapper.selectByPrimaryKey(good.getShopId());
        if (shop == null){
            throw new PetException(ResultEnum.PARAM_ERROR);
        }
        goodsDetailVo.setShopLogo(shop.getLogo());
        if (!shop.getLogo().contains("http")) {
            goodsDetailVo.setShopLogo(IP + shop.getLogo());
        }
        goodsDetailVo.setShopGrade(shop.getGrade());
        goodsDetailVo.setShopName(shop.getShopName());
        goodsDetailVo.setStartBusinessHours(shop.getStartBusinessHours());
        goodsDetailVo.setEndBusinessHours(shop.getEndBusinessHours());
        //查询优惠券（属于该商品可以使用或者领取的）
        //获取满减
        List<CouponInfo> couponInfoList = couponInfoRepository.findByShopIdInAndCpnStateAndCpnType(good.getShopId(), 1,4);
        goodsDetailVo.setCouponInfoFullList(this.assembleCpn(couponInfoList,userId,good.getCategoryId(),good.getShopId()));
        //优惠券
        List<CouponInfo> couponInfos = couponInfoRepository.findByShopIdAndCpnStateAndCpnTypeNot(good.getShopId(), 1, 4);
        if (!CollectionUtils.isEmpty(couponInfos)) {
            couponInfos.stream().forEach(couponInfo -> {
                CpnUser cpnUser = cpnUserRepository.findByUserIdAndCpnIdAndShopId(userId, couponInfo.getId(), String.valueOf(shop.getId()));
                if (cpnUser != null) {
                    //已领取
                    couponInfo.setReceive(1);
                }
                //设置优惠券限制范围名称
                CouponServiceImpl.setCouponScope(couponInfo);
            });
        }
        goodsDetailVo.setCouponInfoList(couponInfos);
        //查询该商品是否被当前用户收藏
        int count = favouriteGoodsRepository.countByUserIdAndGoodIdAndStatus(userId, goodsId, 1);
        goodsDetailVo.setConcernStatus(count);
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
            if (!good.getIcon().contains("http")) {
                goodsListVO.setIcon(IP + good.getIcon());
            }
            //折扣大于0时，才会显示折扣价
            if (good.getDiscount() > 0.0D && good.getDiscount() != null ){
                goodsListVO.setDiscountPrice(good.getPrice().multiply(BigDecimal.valueOf(good.getDiscount()/10).setScale(2, BigDecimal.ROUND_HALF_UP)));
            }
            //商家设置第二件折扣
            if (good.getReDiscount() > 0.0D && good.getReDiscount() != null){
                goodsListVO.setReDiscountDesc("第2件" + good.getReDiscount() + "折");
            }
            //封装优惠券
            //根据店铺查询在架状态的优惠券
            goodsListVO.setCouponInfoList(this.assembleCouponVo(good.getShopId()));
            goodsListVOList.add(goodsListVO);
        });
        return goodsListVOList;
    }


    /**
     * 封装优惠券功能方便复用
     * type:0 代表店铺满减活动（不属于优惠券）;1代表优惠券
     * @return
     */

    public List<CouponInfo> assembleCouponVo(Integer shopId){
        //查询优惠券列表(商品and服务) cpnScopeType: 1全场通用 3限商品 4限服务
        //cpnType:优惠券类型 1现金券 2满减券 3折扣券 4店铺满减
        List<CouponInfo> couponInfoList = couponInfoRepository.findByShopIdInAndCpnState(shopId, 1);
        List<CouponInfo> couponInfos = Lists.newArrayList();
        couponInfoList.stream().forEach(couponInfo -> {
            //查询截止日期与当前日期差
            long result = computerTime(couponInfo.getValidityEndDate());
            if (result > 0) {
                //逻辑处理
                couponInfos.add(couponInfo);
            }
        });
        return couponInfos;
    }


    /**
     * 商品详情 封装当前商品可使用的优惠券
     * @param couponInfoList
     * @return
     */
    public List<CouponInfo> assembleCpn(List<CouponInfo> couponInfoList,Integer userId,Integer categoryId,Integer shopId){
        //查询优惠券列表(商品and服务) cpnScopeType: 1全场通用 3限商品 4限服务
        //cpnType:优惠券类型 1现金券 2满减券 3折扣券 4店铺满减
        List<CouponInfo> couponInfos = Lists.newArrayList();
        couponInfoList.stream().forEach(couponInfo -> {
            CpnUser cpnUser = null;
            if (userId != null) {
                cpnUser = cpnUserRepository.findByUserIdAndCpnIdAndShopId(userId, couponInfo.getId(), String.valueOf(shopId));
            }
            //查询截止日期与当前日期差
            long result = computerTime(couponInfo.getValidityEndDate());
            if (result > 0) {
                //逻辑处理
                //适用范围与当前商品的范围比较，店铺满减除外(未登录用户，需展示未领取的优惠券)
                if (couponInfo.getScopeType() == categoryId && userId == null && couponInfo.getCpnType() != 4){
                    couponInfos.add(couponInfo);
                }else if (couponInfo.getScopeType() == categoryId && userId != null && couponInfo.getCpnType() != 4){
                    //用户未领取该优惠券才会展示
                    if (cpnUser.getCpnId() != couponInfo.getId()){
                        couponInfos.add(couponInfo);
                    }
                }else{
                    couponInfos.add(couponInfo);
                }
            }
        });
        return couponInfos;
    }

    //-------------------------------------------------------------------商户端实现--------------------------------------------------------------------------//


    /**
     * 获取商品类别
     * @return  0 商品 1 服务 2 全部 3全部（剔除父分类）
     */
    @Override
    public ResultResponse getGoodCategoryList(Integer param) {
        List<GoodsType> goodsTypeList = Lists.newArrayList();
        if (0 == param) {
            goodsTypeList = goodsTypeRepository.findByStatusAndCategoryId(1, 3);
            this.goodsTypeList(goodsTypeList);
        }else if (param == 1){
            goodsTypeList = goodsTypeRepository.findByStatusAndCategoryIdNotAndIdNot(1, 3,0);
            this.goodsTypeList(goodsTypeList);
        }else if (param  == 2){
            goodsTypeList = goodsTypeRepository.findAll();
        }else {
            goodsTypeList = goodsTypeRepository.findAll();
            this.goodsTypeList(goodsTypeList);
        }
        return ResultResponse.createBySuccess(goodsTypeList);
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
        if (shopId == null ){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), "shopId不能为空");
        }
        List<GoodsPcVO> goodList = goodMapper.getGoodList(shopId,goodsTypeId, goodName);
        List<GoodsListVO> goodsListVOList = Lists.newArrayList();
        goodList.stream().forEach(good -> {
            GoodsListVO goodsListVO = new GoodsListVO();
            BeanUtils.copyProperties(good,goodsListVO);
            goodsListVOList.add(goodsListVO);
        });
        PageInfo pageResult = new PageInfo(goodList);
        pageResult.setList(goodsListVOList);
        return ResultResponse.createBySuccess(pageResult);
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
     * @param reDiscount 第二件折扣
     * @param goodsTypeId
     * @param discount
     * @return
     */
    @Override
    public ResultResponse discountGood(Integer shopId,Integer goodsTypeId, Double discount, Double reDiscount) {
        if (goodsTypeId == null || discount <= 0 || discount > 9 || shopId == null || reDiscount <= 0 || reDiscount > 9){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        //宠物用品
        if (21 == goodsTypeId) {
            //先找到父id
            List<GoodsType> goodsTypeList = goodsTypeRepository.findByParentId(goodsTypeId);
            List<Integer> ids = Lists.newArrayList();
            goodsTypeList.stream().forEach(goodsType -> {
                ids.add(goodsType.getId());
            });
            goodMapper.updateDiscount(shopId,ids,discount,reDiscount);
        }else {
            goodMapper.goodsDiscount(shopId, goodsTypeId, discount, reDiscount);
        }
        return ResultResponse.createBySuccess();
    }


    /**
     * 获取商品分类(包含服务类、商品类)(不包含父分类)
     * @return
     */
    @Override
    public ResultResponse goodsTypeList() {
        List<GoodsType> goodsTypeList = goodsTypeRepository.findByStatusAndParentIdNot(1,0);
        return ResultResponse.createBySuccess(goodsTypeList);
    }

    /**
     * 增加或编辑商品
     * @param shopId
     * @param goodsListVO
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultResponse saveOrEditGoods(Integer shopId, GoodsListVO goodsListVO) {
        if (shopId == null){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),ResultEnum.PARAM_ERROR.getMessage());
        }
        //如果goodsId为null代表增加，否则为编辑
        Good good = new Good();
        BeanUtils.copyProperties(goodsListVO,good);
        Shop shop = shopRepository.findById(shopId).get();
        good.setAreaCode(shop.getAreaCode());
        if (goodsListVO.getId() == null){
            int result = goodMapper.insert(good);
            if (result == 0){
                return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.SAVE_GOODS_ERROR.getStatus(),
                        GoodsStatusEnum.SAVE_GOODS_ERROR.getMessage());
            }
            return ResultResponse.createBySuccess();
        }
        //编辑
        int result = goodMapper.updateByPrimaryKeySelective(good);
        if (result == 0){
            return ResultResponse.createByErrorCodeMessage(GoodsStatusEnum.SAVE_GOODS_ERROR.getStatus(),
                    GoodsStatusEnum.SAVE_GOODS_ERROR.getMessage());
        }
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse saveGood(Good good) {
        if(good.getId() == null) {
            good.setCreateTime(new Date());
        }
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), goodsRepository.saveAndFlush(good));
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
        if (good.getCategoryId() != null) {
            PetCategory petCategory  = petCategoryRepository.findById(good.getCategoryId()).get();
            goodsListVO.setPetCategoryName(petCategory.getName());
        }
        if (good.getScopeId() != null) {
            ScopeApplication application = applicationRepository.findById(good.getScopeId()).get();
            goodsListVO.setScopeName(application.getName());
        }
        if (good.getBrandId() != null){
            Brand brand = brandRepository.findById(good.getBrandId()).get();
            goodsListVO.setBrandName(brand.getName());
        }
        //如果无洗澡服务内容则展示所有
        //如果存在已经选中的需要展示其他未选中的
        List<BathingService> bathingServiceList = bathingServiceRepository.findAll();
        if (StringUtils.isNotBlank(good.getBathingServiceId())){
            List<String> splitToList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(good.getBathingServiceId());
            bathingServiceList.stream().forEach(bathingService -> {
                for (String s : splitToList) {
                    if (bathingService.getId().equals(Integer.valueOf(s))) {
                        bathingService.setChecked(true);
                    }
                }
            });
        }
        goodsListVO.setBathingServiceList(bathingServiceList);
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
        goodsRepository.updateRatio(shopId);
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
     * 获取洗澡服务内容
     * @return
     */
    @Override
    public ResultResponse getBathingService() {
        List<BathingService> bathingServiceList = bathingServiceRepository.findAll();
        return ResultResponse.createBySuccess(bathingServiceList);
    }

    /**
     * 获取适用期和适用类型
     * @param goodsTypeId
     * @param brandId (废弃) 为了不让前端修改接口，该参数不删除了
     * @return
     */
    @Override
    public ResultResponse getScopeType(Integer goodsTypeId, Integer brandId) {
        if (goodsTypeId == null){
            return ResultResponse.createBySuccess();
        }

        //适用期（根据猫粮和狗粮的goodsTypeId判断适用期有哪些）
        List<ScopeApplication> scopeApplicationList = null;
        //适用类型
        List<PetCategory> petCategoryList = null;
        if (goodsTypeId == 1) {
            //获取狗粮适用期
            scopeApplicationList = scopeApplicationRepository.findByGoodsTypeId(goodsTypeId);
            //获取狗粮适用类型(和适用期绑定)
            petCategoryList = petCategoryRepository.findAll();
            for (ScopeApplication scopeApplication : scopeApplicationList) {
                scopeApplication.setPetCategoryList(petCategoryList);
            }

        }else {
            //猫粮没有适用类型
            //获取猫粮适用期
            scopeApplicationList = scopeApplicationRepository.findByGoodsTypeId(goodsTypeId);
        }
        ScopeVO scopeVO = new ScopeVO();
        scopeVO.setScopeApplicationList(scopeApplicationList);
        return ResultResponse.createBySuccess(scopeVO);
    }

    @Override
    public ResultResponse getPetCategory() {
        //获取狗粮适用类型(和适用期绑定)
        List<PetCategory> petCategoryList = petCategoryRepository.findAll();
        return ResultResponse.createBySuccess(petCategoryList);
    }

    /**
     * 商品收藏/取消
     * @param userId
     * @param goodsId
     * @return
     */
    @Override
    public ResultResponse concernGoods(Integer userId, Integer goodsId) {
        if (goodsId == null){
            return  ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(),"shopId不能为空!");
        }
        //查询商品是否收藏
        FavouriteGood good = favouriteGoodsRepository.findByUserIdAndGoodId(userId,  goodsId);
        //取消收藏
        if (good != null){
            //取消关注
            if (good.getStatus() == 0) {
                good.setStatus(0);
            }else {
                good.setStatus(1);
            }
            good.setUpdateTime(new Date());
            favouriteGoodsRepository.save(good);
        }else {
            //收藏商品
            FavouriteGood favouriteShop = new FavouriteGood();
            favouriteShop.setStatus(1);
            favouriteShop.setGoodId(goodsId);
            favouriteShop.setUserId(userId);
            favouriteShop.setUpdateTime(new Date());
            favouriteShop.setCreateTime(new Date());
            favouriteGoodsRepository.save(favouriteShop);
        }
        return ResultResponse.createBySuccess();
    }

    /**
     * 查看收藏商品列表
     * @param userId
     * @return
     */
    @Override
    public ResultResponse queryConcernGoodsList(Integer userId) {
        List<FavouriteGood> favouriteGoodList = favouriteGoodsRepository.findAllByUserIdAndStatus(userId, 1).orElse(null);
        List<Integer> goodsIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(favouriteGoodList)) {
            favouriteGoodList.stream().forEach(favouriteGood -> {
                goodsIds.add(favouriteGood.getGoodId());
            });
        }
        List<Good> goodList = goodsRepository.findAllByIdIn(goodsIds).orElse(null);
        if (!CollectionUtils.isEmpty(goodList)){
            goodList.stream().forEach(good -> {
                if (!good.getIcon().contains("http")) {
                    good.setIcon(IP + good.getIcon());
                }
            });
        }

        return ResultResponse.createBySuccess(this.goodsListVOList(goodList));
    }

    /**
     * 根据shopId和goodsTypeId获取商品或者服务
     * @param shopId
     * @param id
     * @return
     */
    @Override
    public ResultResponse queryGoodsListByIdAndShopId(Integer shopId, Integer id) {
        if (shopId == null || id == null ) {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), "id或者shopId不能为空");
        }
        List<Good> goodList = goodMapper.findByShopIdAndGoodsTypeId(shopId, id);
        return ResultResponse.createBySuccess(goodList);
    }

    /**
     * 下架商品列表
     * @param shopId
     * @return
     */
    @Override
    public ResultResponse goodsDownList(Integer shopId) {
        List<Good> goodList = goodMapper.findByShopIdAndStatus(shopId);
        return ResultResponse.createBySuccess(goodList);
    }

    /**
     * 查询父分类（包含商品）
     * @param parentId
     * @return
     */
    @Override
    public List<GoodsType> findByParentIdAndStatus(Integer parentId) {
        List<GoodsType> goodsTypeList = goodsTypeRepository.findByParentIdAndStatus(parentId, 1);
        return goodsTypeList;
    }


    /**
     * 去除父分类
     * @param goodsTypeList
     * @return
     */
    private List<GoodsType> goodsTypeList(List<GoodsType> goodsTypeList){
        for (int i = 0; i< goodsTypeList.size(); i++) {
            List<GoodsType> goodsTypes = goodsService.findByParentIdAndStatus(goodsTypeList.get(i).getId());
            //需去除父分类
            if (goodsTypes.size() > 0){
                goodsTypeList.remove(i);
            }
        }
        return goodsTypeList;
    }



}
