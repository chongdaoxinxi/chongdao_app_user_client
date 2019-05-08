package com.chongdao.client.service.iml;

import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.entitys.UserTrans;
import com.chongdao.client.repository.UserTransRepository;
import com.chongdao.client.service.UserTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/8
 * @Version 1.0
 **/
@Service
public class UserTransServiceImpl implements UserTransService {
    @Autowired
    private UserTransRepository userTransRepository;

    @Override
    public void saveUserTarns(UserTrans ut) {
        userTransRepository.saveAndFlush(ut);
    }

    @Override
    public void saveUserTransByRecharge(UserAccount ua, BigDecimal money) {
        UserTrans ut = new UserTrans();
        ut.setUserid(ua.getUserId());
        ut.setMoney(money);
        ut.setComment("用户充值");
        ut.setType(1);
        ut.setCreateTime(new Date());
        userTransRepository.saveAndFlush(ut);
    }
}
