package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.enums.ManageStatusEnum;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.service.ShopManageService;
import com.chongdao.client.utils.MD5Util;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.ShopLoginVO;
import com.chongdao.client.vo.ShopManageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.chongdao.client.common.Const.IP;

/**
 * @Description 商家端
 * @Author onlineS
 * @Date 2019/5/13
 * @Version 1.0
 **/
@Service
public class ShopManageServiceImpl implements ShopManageService {
    @Autowired
    private ShopRepository shopRepository;

    @Override
    public ResultResponse shopLogin(String name, String password) {
        // 非空校验
        if(StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            return ResultResponse.createByErrorCodeMessage(ManageStatusEnum.SHOP_NAME_OR_PASSWORD_EMPTY.getStatus(), ManageStatusEnum.SHOP_NAME_OR_PASSWORD_EMPTY.getMessage());
        }
        //正确性校验
        Optional<Shop> shop = shopRepository.findByAccountName(name);
        if(shop.isPresent()){
            Shop s = shop.get();
            String pwd = s.getPassword();
            String md5Pwd = MD5Util.MD5(password);
            if(StringUtils.isNoneBlank(password) && pwd.equals(md5Pwd)) {
                //密码正确 进入下一步
                return assembleShopLogin(s);
            } else {
                //不正确的密码
                return ResultResponse.createByErrorCodeMessage(ManageStatusEnum.SHOP_ERROR_PASSWORD.getStatus(), ManageStatusEnum.SHOP_ERROR_PASSWORD.getMessage());
            }
        } else {
            // 无效用户名
            return ResultResponse.createByErrorCodeMessage(ManageStatusEnum.SHOP_NOT_EXIST_ERROR.getStatus(), ManageStatusEnum.SHOP_NOT_EXIST_ERROR.getMessage());
        }
    }

    private ResultResponse assembleShopLogin(Shop s) {
        ShopLoginVO sVo = new ShopLoginVO();
        Integer shopId = s.getId();
        sVo.setShopId(s.getId());
        String accountName = s.getAccountName();
        sVo.setAccountName(s.getAccountName());
        Date current = new Date();
        sVo.setLastLoginTime(current);
        //记录下最后登录时间
        s.setLastLoginTime(current);
        shopRepository.saveAndFlush(s);
        //生成token
        sVo.setToken(TokenUtil.generateToken(shopId, accountName, current, RoleEnum.SHOP_APP.getCode()));
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), sVo);
    }

    @Override
    public ResultResponse shopLogout() {
        return null;
    }

    @Override
    public ResultResponse<ShopManageVO> getShopInfo(Integer shopId) {
        return Optional.ofNullable(shopId)
                .flatMap(id -> shopRepository.findById(shopId))
                .map(s -> {
                    ShopManageVO smVo = new ShopManageVO();
                    if (!s.getLogo().contains("http")) {
                        s.setLogo(IP + s.getLogo());
                    }
                    BeanUtils.copyProperties(s, smVo);
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), smVo);
                }).orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    @Override
    public ResultResponse saveShopInfo(Shop shop) {
        return Optional.ofNullable(shop)
                .map(s -> {
                    s.setUpdateTime(new Date());
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), shopRepository.saveAndFlush(s));
                })
                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    @Override
    public ResultResponse saveShopConfig(Integer shopId, Byte isAutoAccept, Integer isInService, String phone) {
        Shop shop = shopRepository.findById(shopId).orElse(null);
        if(isAutoAccept != null) {
            shop.setIsAutoAccept(isAutoAccept);
        }
        if(isInService != null) {
            shop.setStatus(isInService);
        }
        if(StringUtils.isNotBlank(phone)) {
            shop.setPhone(phone);
        }
        shop.setUpdateTime(new Date());
        return ResultResponse.createBySuccess(shopRepository.save(shop));
    }

    @Override
    public ResultResponse downloadQrCodeImg(Integer shopId) {
        Shop shop = shopRepository.findById(shopId).orElse(null);
        if(shop != null) {

        }
        return null;
    }

    @Override
    public ResultResponse changeShopStopStatus(Integer shopId, Integer targetStatus) {
        Shop shop = shopRepository.findById(shopId).orElse(null);
        shop.setIsStop((byte)targetStatus.intValue());
        shop.setUpdateTime(new Date());
        return ResultResponse.createBySuccess(shopRepository.save(shop));
    }
}
