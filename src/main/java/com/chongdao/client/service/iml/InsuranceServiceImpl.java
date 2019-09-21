package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.entitys.InsuranceOrderAudit;
import com.chongdao.client.entitys.InsuranceShopChip;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.InsuranceOrderMapper;
import com.chongdao.client.repository.InsuranceOrderAuditRepository;
import com.chongdao.client.repository.InsuranceOrderRepository;
import com.chongdao.client.repository.InsuranceShopChipRepository;
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
    @Autowired
    private InsuranceShopChipRepository insuranceShopChipRepository;

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
        if (order.getId() == null) {
            //新订单, 新生成的数据
            //生成订单号
            order.setInsuranceOrderNo(InsuranceUUIDUtil.generateUUID());//订单号设置为保险投保接口所需要的UUID, 作为两边对接的唯一标识

            //如果投保人与被保人关系为本人时, 复制投保人信息至被保人
            order.setAcceptSeqNo(1);
            if ((order.getInsuranceType() != null && order.getInsuranceType() != 2) || ( order.getBeneficiary() != null && order.getBeneficiary() == 0)) {
                //非家责险或者被保人与投保人关系为别人
                order.setAcceptName(order.getName());
                order.setAcceptPhone(order.getPhone());
                if(order.getEmail() != null) {
                    order.setAcceptMail(order.getEmail());
                }
                if(order.getAddress() != null) {
                    order.setAcceptAddress(order.getAddress());
                }
                order.setAcceptCardType(order.getCardType());
                order.setAcceptCardNo(order.getCardNo());
            }

            //校验宠物芯片是否被使用
            Integer medicalInsuranceShopChipId = order.getMedicalInsuranceShopChipId();
            if (medicalInsuranceShopChipId != null) {
                InsuranceShopChip insuranceShopChip = insuranceShopChipRepository.findById(medicalInsuranceShopChipId).orElse(null);
                if (insuranceShopChip == null) {
                    return ResultResponse.createByErrorMessage("无效的宠物芯片, 请重新选择!");
                } else {
                    if (insuranceShopChip.getStatus() != 1) {
                        return ResultResponse.createByErrorMessage("所选宠物芯片已被使用, 请重新选择!");
                    } else {
                        insuranceShopChip.setStatus(0);
                        insuranceShopChip.setSelectedTime(new Date());//更新被选中时间
                        insuranceShopChipRepository.save(insuranceShopChip);//更新所选宠物芯片的状态
                    }
                }
            }

            //for test
            order.setCardType("01");
            order.setAcceptCardType("01");


            //设置一些默认参数
            order.setIsSendMsg(1);//默认发送短消息
            order.setStatus(0);
            order.setCreateTime(new Date());
        }
        order.setApplyTime(new Date());

        InsuranceOrder savedOrder = insuranceOrderRepository.save(order);
        //如果要加入审核机制, 那么这里需要写一些处理逻辑, 区分是保存订单还是付款后的请求外部接口生成订单

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
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), insuranceOrderMapper.getInsuranceOrderDetail(insuranceId));
    }

    @Override
    public ResultResponse downloadElectronicInsurancePolicy(Integer insuranceId) {
        return null;
    }

    @Override
    public ResultResponse getInsuranceDataList(String token, Integer insuranceType, String userName, String phone, String insuranceOrderNo, Date start, Date end, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //根据token, 加入地区areaCode限制

        List<InsuranceOrder> insuranceDataList = insuranceOrderMapper.getInsuranceDataList(insuranceType, userName, phone, insuranceOrderNo, start, end, status);
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

    @Override
    public ResultResponse pollingCheckOrderStatus(Integer insuranceOrderId) {
        InsuranceOrder insuranceOrder = insuranceOrderRepository.findById(insuranceOrderId).orElse(null);
        if(insuranceOrder == null) {
            return ResultResponse.createByErrorMessage("无效的订单ID!");
        }
        return ResultResponse.createBySuccess(insuranceOrder.getStatus());
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
