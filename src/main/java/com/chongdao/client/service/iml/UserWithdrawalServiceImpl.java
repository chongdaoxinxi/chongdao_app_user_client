package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.User;
import com.chongdao.client.entitys.UserWithdrawal;
import com.chongdao.client.mapper.UserWithdrawalMapper;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.repository.UserWithdrawalRepository;
import com.chongdao.client.service.CashAccountService;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.service.UserWithdrawalService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.utils.sms.SMSUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserWithdrawalServiceImpl implements UserWithdrawalService {
    @Autowired
    private UserWithdrawalRepository userWithdrawalRepository;
    @Autowired
    private UserWithdrawalMapper userWithdrawalMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SmsService smsService;
    @Autowired
    protected SMSUtil smsUtil;
    @Autowired
    private CashAccountService cashAccountService;

    @Transactional
    @Override
    public ResultResponse addUserWithdrawal(UserWithdrawal userWithdrawal) {
        Integer id = userWithdrawal.getId();
        BigDecimal money = userWithdrawal.getMoney();
        Integer userId = userWithdrawal.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        BigDecimal left = user.getMoney();
        if (money == null || left == null) {
            return ResultResponse.createByErrorMessage("申请提现金额不能为null, 用户余额不能为null!");
        }
        if (money.compareTo(left) > 1) {
            return ResultResponse.createByErrorMessage("申请提现金额不能大于用户余额!");
        }
        if (id == null) {
            UserWithdrawal add = new UserWithdrawal();
            BeanUtils.copyProperties(userWithdrawal, add);
            add.setCreateTime(new Date());
            add.setStatus(0);//默认状态为待审核
            add.setName(user.getName());//防止前台传过来修改昵称后没来的及刷新的昵称
            userWithdrawalRepository.save(add);

        } else {
            userWithdrawal.setUpdateTime(new Date());
            userWithdrawalRepository.save(userWithdrawal);
        }
        //完成资金处理逻辑
        cashAccountService.userWithdrawal(userWithdrawal, false);
        try {
            //短信通知管理员处理提现
            String superAdminPhone = smsService.getSuperAdminPhone();
            smsService.customMsgSenderSimple(smsUtil.getUserWithdrawalAdmin(), superAdminPhone);
        } catch (Exception e) {
            log.error("用户申请提现短信通知管理员失败!");
        }
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse getUserWithdrawalList(String token, String name, String phone, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Integer userId = null;
        if (StringUtils.isNotBlank(token)) {
            ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
            userId = tokenVo.getUserId();
        }
        if(status != null && status == 99) {
            status = null;
        }
        List<UserWithdrawal> list = userWithdrawalMapper.getUserWithdrawalList(userId, name, phone, status, startDate, endDate);
        PageInfo pageInfo = new PageInfo(list);
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

        String msg = smsUtil.getUserWithdrawalSuccessUser();
        if(targetStatus == -1) {
            //审核被拒绝, 资金退还处理
            cashAccountService.userWithdrawal(userWithdrawal, true);
            msg = smsUtil.getUserWithdrawalFailUser();
        }
        Integer userId = userWithdrawal.getUserId();
        try {
            User user = userRepository.findById(userId).orElse(null);
            //发送短信通知(提现审核通过/失败)
            smsService.customMsgSenderSimple(msg, user.getPhone());
        } catch (Exception e) {
            log.error("用户提现成功短信提醒用户失败!");
        }
        return ResultResponse.createBySuccess();
    }
}
