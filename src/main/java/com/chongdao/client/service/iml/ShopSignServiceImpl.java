package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.entitys.ShopSignInfo;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.repository.ShopSignInfoRepository;
import com.chongdao.client.service.ShopSignService;
import com.chongdao.client.utils.MD5Util;
import com.chongdao.client.vo.ShopSignTypeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/12/27
 * @Version 1.0
 **/
@Service
public class ShopSignServiceImpl implements ShopSignService {
    @Autowired
    private ShopSignInfoRepository shopSignInfoRepository;
    @Autowired
    private ShopRepository shopRepository;

    /**
     * 申请商家入驻
     *
     * @param shopSignInfo
     * @return
     */
    @Override
    @Transactional
    public ResultResponse applyShopSign(ShopSignInfo shopSignInfo) {
        ShopSignInfo ssi = new ShopSignInfo();
        BeanUtils.copyProperties(shopSignInfo, ssi);
        if(ssi.getId() == null) {
            ssi.setCreateTime(new Date());
        } else {
            ssi.setUpdateTime(new Date());
        }
        ssi.setStatus(0);//待审核
        return ResultResponse.createBySuccess(shopSignInfoRepository.save(ssi));
    }

    @Override
    public ResultResponse getShopType() {
        List<ShopSignTypeVO> list = new ArrayList<>();
        list.add(new ShopSignTypeVO(0, "普通宠物店"));
        list.add(new ShopSignTypeVO(4, "活体宠物店"));
        list.add(new ShopSignTypeVO(2, "普通宠物医院"));
        list.add(new ShopSignTypeVO(3, "宠物医保宠物医院"));
        return ResultResponse.createBySuccess(list);
    }

    @Override
    public ResultResponse getMySignList(Integer userId) {
        return ResultResponse.createBySuccess(shopSignInfoRepository.findByUserId(userId));
    }

    @Override
    public ResultResponse getSignList(Integer pageSize, Integer pageNum, String shopName) {
        pageNum = pageNum - 1;
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "createTime");
        if(StringUtils.isNotBlank(shopName)) {
            shopName = "%" + shopName + "%";
            return ResultResponse.createBySuccess(shopSignInfoRepository.findByNameLike(shopName, pageable));
        }
        return ResultResponse.createBySuccess(shopSignInfoRepository.findAll(pageable));
    }

    @Override
    @Transactional
    public ResultResponse auditShopSign(Integer signShopId, Integer targetStatus) {
        ShopSignInfo shopSignInfo = shopSignInfoRepository.findById(signShopId).orElse(null);
        if(shopSignInfo != null) {
            shopSignInfo.setStatus(targetStatus);
            shopSignInfo.setUpdateTime(new Date());
            ShopSignInfo save = shopSignInfoRepository.save(shopSignInfo);
            if(targetStatus == 1) {
                //审核通过
                Shop shop = typeInShopSignDataToShop(shopSignInfo);
                Shop save1 = shopRepository.save(shop);
                save.setShopId(save1.getId());
                shopSignInfoRepository.save(save);
            }
        }
        return ResultResponse.createBySuccess();
    }

    private Shop typeInShopSignDataToShop(ShopSignInfo shopSignInfo) {
        Shop shop = new Shop();
        shop.setShopName(shopSignInfo.getName());
        shop.setPhone(shopSignInfo.getAcceptPhone());
        shop.setAccountName(shopSignInfo.getAcceptPhone());
        shop.setPassword(MD5Util.MD5("12345678"));
        shop.setAddress(shopSignInfo.getAddress());
        shop.setAreaCode(shopSignInfo.getAreaCode());
        if(StringUtils.isNotBlank(shopSignInfo.getAreaId())) {
            shop.setAreaId(Integer.valueOf(shopSignInfo.getAreaId()));
        }
        shop.setType(shopSignInfo.getType());
        shop.setLogo(shopSignInfo.getLogo());
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());

        //////////////////////
        shop.setMoney(new BigDecimal(0));
        shop.setArriveShopOrderMoney(new BigDecimal(0));
        shop.setCustomGoodOrderMoney(new BigDecimal(0));
        shop.setCustomServiceOrderMoney(new BigDecimal(0));
        shop.setInsuranceMoney(new BigDecimal(0));
        shop.setRecommendMoney(new BigDecimal(0));
        shop.setDiscount(0d);
        shop.setGrade(0d);
        shop.setIsAutoAccept((byte)-1);
        shop.setIsStop((byte)-1);
        shop.setStatus(1);
        shop.setIsHot((byte)-1);
        shop.setIsJoinCommonWeal((byte)-1);
        return shop;
    }
}
