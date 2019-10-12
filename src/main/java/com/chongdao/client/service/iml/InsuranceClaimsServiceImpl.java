package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceClaims;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.mapper.InsuranceClaimsMapper;
import com.chongdao.client.repository.InsuranceClaimsRepository;
import com.chongdao.client.repository.InsuranceOrderRepository;
import com.chongdao.client.service.InsuranceClaimsService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/9
 * @Version 1.0
 **/
@Service
public class InsuranceClaimsServiceImpl implements InsuranceClaimsService {
    @Autowired
    private InsuranceClaimsRepository insuranceClaimsRepository;
    @Autowired
    private InsuranceClaimsMapper insuranceClaimsMapper;
    @Autowired
    private InsuranceOrderRepository insuranceOrderRepository;

    @Transactional
    @Override
    public ResultResponse saveInsuranceClaims(InsuranceClaims insuranceClaims) {
        Integer id = insuranceClaims.getId();
        Integer medicalInsuranceOrderId = insuranceClaims.getMedicalInsuranceOrderId();
        if (medicalInsuranceOrderId == null) {
            return ResultResponse.createByErrorMessage("保险订单ID不能为空!");
        }
        InsuranceOrder insuranceOrder = insuranceOrderRepository.findById(medicalInsuranceOrderId).orElse(null);
        if (insuranceOrder == null) {
            return ResultResponse.createByErrorMessage("无效的保险订单ID");
        }
        if (id != null) {
            //编辑
            insuranceClaims.setUpdateTime(new Date());
            insuranceClaimsRepository.save(insuranceClaims);
        } else {
            //新建
            InsuranceClaims add = new InsuranceClaims();
            BeanUtils.copyProperties(insuranceClaims, add);
            add.setCreateTime(new Date());
//            //冗余部分保险订单数据
//            if (StringUtils.isNotBlank(insuranceOrder.getPetName()))
//                add.setPetName(insuranceOrder.getPetName());
//            if (StringUtils.isNotBlank(insuranceOrder.getPetPhoto()))
//                add.setPetPhoto(insuranceOrder.getPetPhoto());
//            if (StringUtils.isNotBlank(insuranceOrder.getPetBreedName()))
//                add.setPetBreedName(insuranceOrder.getPetBreedName());
//            if (insuranceOrder.getPetAge() != null)
//                add.setPetAge(insuranceOrder.getPetAge());
            insuranceClaimsRepository.save(add);
        }
        if(insuranceClaims.getAuditStatus() != null && insuranceClaims.getAuditStatus() == 0) {
            insuranceOrder.setStatus(4);//将保单状态修改为理赔中
            insuranceOrderRepository.save(insuranceOrder);
        }
        return ResultResponse.createBySuccess();
    }

    @Transactional
    @Override
    public ResultResponse removeInsuranceClaims(Integer claimsId) {
        insuranceClaimsRepository.deleteById(claimsId);
        return ResultResponse.createBySuccess();
    }

    @Transactional
    @Override
    public ResultResponse confirmInsuranceClaimsMoney(Integer claimsId) {
        InsuranceClaims insuranceClaims = insuranceClaimsRepository.findById(claimsId).orElse(null);
        if (insuranceClaims != null) {
            insuranceClaims.setAuditStatus(4);
            insuranceClaimsRepository.save(insuranceClaims);
            return ResultResponse.createBySuccess();
        } else {
            return ResultResponse.createByErrorMessage("无效的理赔ID");
        }
    }

    @Override
    public ResultResponse getMyClaimsList(String token, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        List<InsuranceClaims> list = insuranceClaimsMapper.getMyClaimsList(tokenVo.getUserId(), startDate, endDate);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    public ResultResponse getAppiontInsuranceOrderClaims(Integer insuranceOrderId) {
        return ResultResponse.createBySuccess(insuranceClaimsMapper.getClaimsByInsuranceOrderId(insuranceOrderId));
    }

    @Override
    public ResultResponse getClaimsDetail(Integer claimsId) {
        return ResultResponse.createBySuccess(insuranceClaimsRepository.findById(claimsId).orElse(null));
    }
}
