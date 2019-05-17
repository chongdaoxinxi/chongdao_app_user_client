package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.ExpressManageService;
import org.springframework.stereotype.Service;

/**
 * @Description 配送端
 * @Author onlineS
 * @Date 2019/5/17
 * @Version 1.0
 **/
@Service
public class ExpressManageServiceImpl implements ExpressManageService {
    @Override
    public ResultResponse expressLogin(String username, String password) {
//        // 非空校验
//        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
//            return ResultResponse.createByErrorCodeMessage(ShopManageStatusEnum.SHOP_NAME_OR_PASSWORD_EMPTY.getStatus(), ShopManageStatusEnum.SHOP_NAME_OR_PASSWORD_EMPTY.getMessage());
//        }
//        //正确性校验
//        Optional<Shop> shop = shopRespository.findByAccountName(username);
//        if(shop.isPresent()){
//            Shop s = shop.get();
//            String pwd = s.getPassword();
//            if(StringUtils.isNoneBlank(pwd) && pwd.equals(password)) {
//                //密码正确 进入下一步
//                return assembleShopLogin(s);
//            } else {
//                //不正确的密码
//                return ResultResponse.createByErrorCodeMessage(ShopManageStatusEnum.SHOP_ERROR_PASSWORD.getStatus(), ShopManageStatusEnum.SHOP_ERROR_PASSWORD.getMessage());
//            }
//        } else {
//            // 无效用户名
//            return ResultResponse.createByErrorCodeMessage(ShopManageStatusEnum.SHOP_NOT_EXIST_ERROR.getStatus(), ShopManageStatusEnum.SHOP_NOT_EXIST_ERROR.getMessage());
//        }
        return null;
    }

    @Override
    public ResultResponse expressLogout(String username, String password) {
        return null;
    }
}
