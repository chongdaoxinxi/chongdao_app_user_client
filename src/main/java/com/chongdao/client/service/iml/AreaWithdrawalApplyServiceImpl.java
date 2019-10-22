package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.AreaWithdrawalApply;
import com.chongdao.client.enums.DeductPercentEnum;
import com.chongdao.client.mapper.AreaWithdrawalApplyMapper;
import com.chongdao.client.repository.AreaWithdrawalApplyRepository;
import com.chongdao.client.service.AreaWithdrawalApplyService;
import com.chongdao.client.service.CashAccountService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/1
 * @Version 1.0
 **/
@Service
public class AreaWithdrawalApplyServiceImpl implements AreaWithdrawalApplyService {
    @Autowired
    private AreaWithdrawalApplyMapper areaWithdrawalApplyMapper;
    @Autowired
    private AreaWithdrawalApplyRepository areaWithdrawalApplyRepository;
    @Autowired
    private CashAccountService cashAccountService;

    @Override
    @Transactional
    public ResultResponse applyAreaWithdrawal(String token, BigDecimal applyMoney, String applyNote) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        AreaWithdrawalApply areaWithdrawalApply = new AreaWithdrawalApply();
        areaWithdrawalApply.setManagementId(tokenVo.getUserId());
        areaWithdrawalApply.setApplyMoney(applyMoney);
        areaWithdrawalApply.setApplyNote(applyNote);
        areaWithdrawalApply.setStatus(0);
        areaWithdrawalApply.setCreateTime(new Date());
        areaWithdrawalApply.setDeductRate(DeductPercentEnum.AREA_WITHDRAWAL_DEDUCT.getCode());
        areaWithdrawalApplyRepository.save(areaWithdrawalApply);
        //申请后, 完成资金扣除, 并生成流水
        cashAccountService.areaAdminWithdrawal(areaWithdrawalApply, false);
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse getAreaWithdrawApplyListData(Integer managementId, String name, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if(status != null && status == 99) {
            status = null;
        }
        return ResultResponse.createBySuccess(new PageInfo(areaWithdrawalApplyMapper.getAreaWithdrawApplyList(managementId, name, status, startDate, endDate)));
    }

    @Override
    @Transactional
    public ResultResponse checkAreaWithdrawal(Integer userWithdrawalId, String note, BigDecimal realMoney, Integer targetStatus) {
        AreaWithdrawalApply areaWithdrawalApply = areaWithdrawalApplyRepository.findById(userWithdrawalId).orElse(null);
        if(areaWithdrawalApply == null) {
            return ResultResponse.createByErrorMessage("无效的地区提现ID, 请联系管理员!");
        }
        areaWithdrawalApply.setRealMoney(realMoney);
        areaWithdrawalApply.setStatus(targetStatus);
        areaWithdrawalApply.setCheckNote(note);
        areaWithdrawalApply.setCheckTime(new Date());
        areaWithdrawalApply.setUpdateTime(new Date());
        areaWithdrawalApplyRepository.save(areaWithdrawalApply);
        if(targetStatus == -1) {
            //如果审核拒绝, 将资金退回地区账户
            cashAccountService.areaAdminWithdrawal(areaWithdrawalApply, true);
        }
        return ResultResponse.createBySuccess();
    }
}
