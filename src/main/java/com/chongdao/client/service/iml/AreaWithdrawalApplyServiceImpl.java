package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.AreaWithdrawalApply;
import com.chongdao.client.mapper.AreaWithdrawalApplyMapper;
import com.chongdao.client.repository.AreaWithdrawalApplyRepository;
import com.chongdao.client.service.AreaWithdrawalApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ResultResponse getAreaWithdrawApplyListData(Integer managementId, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResultResponse.createBySuccess(new PageInfo(areaWithdrawalApplyMapper.getAreaWithdrawApplyList(managementId, startDate, endDate)));
    }

    @Override
    public ResultResponse checkAreaWithdrawal(Integer userWithdrawalId, String note, BigDecimal realMoney, Integer targetStatus) {
        AreaWithdrawalApply areaWithdrawalApply = areaWithdrawalApplyRepository.findById(userWithdrawalId).orElse(null);
        if(areaWithdrawalApply == null) {
            return ResultResponse.createByErrorMessage("无效的地区提现ID, 请联系管理员!");
        }

        return null;
    }
}
