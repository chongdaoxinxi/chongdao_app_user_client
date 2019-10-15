package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.UserWithdrawal;
import com.chongdao.client.mapper.UserWithdrawalMapper;
import com.chongdao.client.repository.UserWithdrawalRepository;
import com.chongdao.client.service.UserWithdrawalService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/15
 * @Version 1.0
 **/
@Service
public class UserWithdrawalServiceImpl implements UserWithdrawalService {
    @Autowired
    private UserWithdrawalRepository userWithdrawalRepository;
    @Autowired
    private UserWithdrawalMapper userWithdrawalMapper;

    @Transactional
    @Override
    public ResultResponse addUserWithdrawal(UserWithdrawal userWithdrawal) {
        Integer id = userWithdrawal.getId();
        if (id == null) {
            UserWithdrawal add = new UserWithdrawal();
            BeanUtils.copyProperties(add, userWithdrawal);
            add.setCreateTime(new Date());
            add.setStatus(0);
            userWithdrawalRepository.save(add);
            return ResultResponse.createBySuccess();
        } else {
            userWithdrawal.setUpdateTime(new Date());
            userWithdrawalRepository.save(userWithdrawal);
            return ResultResponse.createBySuccess();
        }
    }

    @Override
    public ResultResponse getUserWithdrawalList(String token, String name, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Integer userId = null;
        if (StringUtils.isNotBlank(token)) {
            ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
            userId = tokenVo.getUserId();
        }
        List<UserWithdrawal> list = userWithdrawalMapper.getUserWithdrawalList(userId, name, null, startDate, endDate);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(list);
        return ResultResponse.createBySuccess(pageInfo);
    }

    @Transactional
    @Override
    public ResultResponse checkUserWithdrawal(Integer userWithdrawalId, String note, BigDecimal realMoney, Integer targetStatus) {
        UserWithdrawal userWithdrawal = userWithdrawalRepository.findById(userWithdrawalId).orElse(null);
        if (userWithdrawal == null) {
            return ResultResponse.createByErrorMessage("无效的用户提现ID");
        }
        userWithdrawal.setCheckTime(new Date());
        userWithdrawal.setUpdateTime(new Date());
        userWithdrawal.setCheckNote(note);
        userWithdrawal.setRealMoney(realMoney);
        userWithdrawal.setStatus(targetStatus);
        userWithdrawalRepository.save(userWithdrawal);

        //添加用户账号金额交易记录

        //发送短信通知(提现审核通过)
        return ResultResponse.createBySuccess();
    }
}
