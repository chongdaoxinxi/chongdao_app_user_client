package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.entitys.UserTrans;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.UserTransRepository;
import com.chongdao.client.service.UserTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        ut.setUserId(ua.getUserId());
        ut.setMoney(money);
        ut.setComment("用户充值");
        ut.setType(1);
        ut.setCreateTime(new Date());
        userTransRepository.saveAndFlush(ut);
    }

    @Override
    public ResultResponse<Page<UserTrans>> getUserTrans(Integer userId, Integer type, Integer pageNum, Integer pageSize) {
        if(userId != null && type != null && pageNum != null && pageSize != null) {
            Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "createTime");
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userTransRepository.findByUserIdAndType(userId, type, pageable));
        } else {
            return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
        }
    }
}
