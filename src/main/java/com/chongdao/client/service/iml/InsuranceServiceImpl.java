package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.InsuranceOrderRepository;
import com.chongdao.client.service.insurance.InsuranceExternalService;
import com.chongdao.client.service.insurance.InsuranceService;
import com.chongdao.client.utils.GenerateOrderNo;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    /**
     * 保存保单数据
     * @return
     */
    @Override
    @Transactional
    public ResultResponse saveInsurance(InsuranceOrder insuranceOrder) {
        //先将数据保存在我们数据库
        InsuranceOrder order = new InsuranceOrder();
        BeanUtils.copyProperties(insuranceOrder, order);
        //生成订单号
        order.setInsuranceOrderNo(GenerateOrderNo.genUniqueKey());
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
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), insuranceOrderRepository.findByUserId(userId, pageable));
    }

    @Override
    public ResultResponse getInsuranceDetail(Integer insuranceId) {
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), insuranceOrderRepository.findById(insuranceId));
    }

    @Override
    public ResultResponse getInsuranceDataList(String token, String userName, String insuranceOrderNo, Date start, Date end, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public ResultResponse auditInsurance(Integer insuranceOrderId, Integer targetStatus, String note) {
        return null;
    }

    @Override
    public ResultResponse refuseInsurance(Integer insuranceOrderId, Integer targetStatus, String note) {
        return null;
    }
}
