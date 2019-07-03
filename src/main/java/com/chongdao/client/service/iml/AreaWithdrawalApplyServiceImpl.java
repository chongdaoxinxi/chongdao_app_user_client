package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.mapper.AreaWithdrawalApplyMapper;
import com.chongdao.client.service.AreaWithdrawalApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public ResultResponse getAreaWithdrawApplyListData(Integer managementId, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResultResponse.createBySuccess(new PageInfo(areaWithdrawalApplyMapper.getAreaWithdrawApplyList(managementId, startDate, endDate)));
    }
}
