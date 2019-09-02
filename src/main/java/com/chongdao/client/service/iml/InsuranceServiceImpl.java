package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.entitys.InsuranceOrderAudit;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.InsuranceOrderMapper;
import com.chongdao.client.repository.InsuranceOrderAuditRepository;
import com.chongdao.client.repository.InsuranceOrderRepository;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import com.chongdao.client.service.insurance.InsuranceService;
import com.chongdao.client.utils.InsuranceUUIDUtil;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/8/2
 * @Version 1.0
 **/
@Service
public class InsuranceServiceImpl implements InsuranceService {
    @Autowired
    private InsuranceExternalService insuranceExternalService;
    @Autowired
    private InsuranceOrderRepository insuranceOrderRepository;
    @Autowired
    private InsuranceOrderMapper insuranceOrderMapper;
    @Autowired
    private InsuranceOrderAuditRepository insuranceOrderAuditRepository;

    /**
     * 保存保单数据
     *
     * @return
     */
    @Override
    @Transactional
    public ResultResponse saveInsurance(InsuranceOrder insuranceOrder) throws IOException {
        //先将数据保存在我们数据库
        InsuranceOrder order = new InsuranceOrder();
        BeanUtils.copyProperties(insuranceOrder, order);
        //生成订单号
        order.setInsuranceOrderNo(InsuranceUUIDUtil.generateUUID());//订单号设置为保险投保接口所需要的UUID, 作为两边对接的唯一标识
        //设置一些默认参数
        order.setIsSendMsg(1);//默认发送短消息
        order.setBeneficiary(1);//被保人与投保人关系, 默认为本人
        order.setCreateTime(new Date());
        InsuranceOrder savedOrder = insuranceOrderRepository.save(order);

        //请求外部接口, 生成保单
        return insuranceExternalService.generateInsure(savedOrder);
    }

    @Override
    public ResultResponse getMyInsuranceData(String token, Integer pageSize, Integer pageNum) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        Integer userId = tokenVo.getUserId();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), insuranceOrderRepository.findByUserId(userId, pageable));
    }

    @Override
    public ResultResponse getInsuranceDetail(Integer insuranceId) {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), insuranceOrderRepository.findById(insuranceId));
    }

    @Override
    public ResultResponse downloadElectronicInsurancePolicy(Integer insuranceId) {
        return null;
    }

    @Override
    public ResultResponse getInsuranceDataList(String token, String userName, String insuranceOrderNo, Date start, Date end, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<InsuranceOrder> insuranceDataList = insuranceOrderMapper.getInsuranceDataList(userName, insuranceOrderNo, start, end);
        PageInfo pageResult = new PageInfo(insuranceDataList);
        pageResult.setList(insuranceDataList);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    @Transactional
    public ResultResponse auditInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note) {
        updateInsuranceOrderStatus(token, insuranceOrderId, targetStatus, note);
        return ResultResponse.createBySuccessMessage("审核通过成功!");
    }

    @Override
    @Transactional
    public ResultResponse refuseInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note) {
        updateInsuranceOrderStatus(token, insuranceOrderId, targetStatus, note);
        return ResultResponse.createBySuccessMessage("拒绝审核成功!");
    }

    private void updateInsuranceOrderStatus(String token, Integer insuranceOrderId, Integer targetStatus, String note) {
        InsuranceOrder insuranceOrder = insuranceOrderRepository.findById(insuranceOrderId).orElse(null);
        insuranceOrder.setStatus(targetStatus);
        insuranceOrderRepository.save(insuranceOrder);
        //生成审核记录
        Integer oldStatus = insuranceOrder.getStatus();
        InsuranceOrderAudit insuranceOrderAudit = new InsuranceOrderAudit();
        insuranceOrderAudit.setInsuranceOrderId(insuranceOrderId);
        insuranceOrderAudit.setOldStatus(oldStatus);
        insuranceOrderAudit.setNewStatus(targetStatus);
        insuranceOrderAudit.setNote(note);
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        insuranceOrderAudit.setManagementId(tokenVo.getUserId());
        insuranceOrderAuditRepository.save(insuranceOrderAudit);
    }
}
