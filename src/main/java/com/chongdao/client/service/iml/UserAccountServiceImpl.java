package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.UserAccountRepository;
import com.chongdao.client.service.UserAccountService;
import com.chongdao.client.service.UserTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.OpenOption;
import java.util.Optional;

/**
 * @Description 用户账户
 * @Author onlineS
 * @Date 2019/4/28
 * @Version 1.0
 **/
@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserTransService userTransService;

    @Override
    public ResultResponse<UserAccount> getUserAccountByUserId(Integer userId) {
        if(Optional.ofNullable(userId).isPresent()) {
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAccountRepository.findByUserId(userId));
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse saveUserAccount(UserAccount ua) {
        if(Optional.ofNullable(ua).isPresent()) {
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAccountRepository.saveAndFlush(ua));
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }

    @Override
    public ResultResponse rechargeAccount(Integer userId, BigDecimal money) {
        if(userId != null && money != null) {
            UserAccount ua = userAccountRepository.findByUserId(userId);
            if(ua != null) {
                BigDecimal old_money = ua.getMoney();
                if(old_money != null) {
                    ua.setMoney(old_money.add(money));
                } else {
                    ua.setMoney(money);
                }
                UserAccount userAccount = userAccountRepository.saveAndFlush(ua);
                userTransService.saveUserTransByRecharge(userAccount, money);
                return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAccountRepository.saveAndFlush(ua));
            } else {
                UserAccount newUa = new UserAccount();
                newUa.setUserId(userId);
                newUa.setStatus(1);
                newUa.setMoney(money);
                UserAccount userAccount = userAccountRepository.saveAndFlush(newUa);
                userTransService.saveUserTransByRecharge(userAccount, money);
                return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userAccountRepository.saveAndFlush(newUa));
            }
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }
}
