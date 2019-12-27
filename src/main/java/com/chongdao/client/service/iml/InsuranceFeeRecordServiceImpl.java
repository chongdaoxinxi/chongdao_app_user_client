package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceFeeRecord;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.mapper.InsuranceFeeRecordMapper;
import com.chongdao.client.repository.InsuranceFeeRecordRepository;
import com.chongdao.client.service.InsuranceFeeRecordService;
import com.chongdao.client.utils.DateTimeUtil;
import com.chongdao.client.utils.GenerateOrderNo;
import com.chongdao.client.utils.InsuranceFeePaymentVoucherUtil;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.PaymentVoucherVO;
import com.chongdao.client.vo.ResultTokenVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/9/6
 * @Version 1.0
 **/
@Service
public class InsuranceFeeRecordServiceImpl implements InsuranceFeeRecordService {
    @Autowired
    private InsuranceFeeRecordMapper insuranceFeeRecordMapper;
    @Autowired
    private InsuranceFeeRecordRepository insuranceFeeRecordRepository;

    @Override
    public ResultResponse getInsuranceFeeRecordData(String token, String userName, String shopName, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        List<InsuranceFeeRecord> list = insuranceFeeRecordMapper.getInsuranceFeeRecordData(tokenVo.getUserId(), userName, shopName, status, startDate, endDate);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    public ResultResponse getUserFeeRecordList(String token, Integer status, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        List<InsuranceFeeRecord> list = insuranceFeeRecordMapper.getUserInsuranceFeeRecordList(tokenVo.getUserId(), status, startDate, endDate);
        PageInfo pageResult = new PageInfo(list);
        pageResult.setList(list);
        return ResultResponse.createBySuccess(pageResult);
    }

    @Override
    public ResultResponse addInsuranceFeeRecord(InsuranceFeeRecord insuranceFeeRecord) {
        InsuranceFeeRecord ifr = new InsuranceFeeRecord();
        BeanUtils.copyProperties(insuranceFeeRecord, ifr);
        ifr.setOrderNo("IFR_" + GenerateOrderNo.genUniqueKey());
        ifr.setCreateTime(new Date());
        ifr.setStatus(-1);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), insuranceFeeRecordRepository.save(ifr));
    }

    @Override
    public ResultResponse payInsuranceFee(HttpServletRequest req, String insuranceFeeRecordNo, Integer totalFee, String goodStr, String openId, Integer payType) {
        //TODO 支付宝以及微信支付 需要将方法抽取出来 方便各种方式的支付
        return null;
    }

    @Override
    public ResultResponse confirmMyTodoFeeRecord(Integer insuranceFeeRecordId) {
        return null;
    }

    @Override
    public ResultResponse getInsuranceFeeRecordDetail(Integer insuranceFeeRecordId) {
        return ResultResponse.createBySuccess(insuranceFeeRecordMapper.getInsuranceFeeRecordById(insuranceFeeRecordId));
    }

    @Override
    public ResultResponse generateInsuranceFeeRecordCertificate(Integer insuranceFeeRecordId) {
        InsuranceFeeRecord insuranceFeeRecord = insuranceFeeRecordRepository.findById(insuranceFeeRecordId).orElse(null);
        if(insuranceFeeRecord != null) {
            PaymentVoucherVO pv = new PaymentVoucherVO();
            pv.setShopName(insuranceFeeRecord.getShopName());
            pv.setOrderNo(insuranceFeeRecord.getOrderNo());
            pv.setMoney(insuranceFeeRecord.getMoney());
            pv.setPayType("保险费用支付");
            pv.setCreateTime(DateTimeUtil.dateToStr(new Date()));
            System.out.println(InsuranceFeePaymentVoucherUtil.graphicsGeneration(pv));
            return ResultResponse.createBySuccess();
        }
        return ResultResponse.createByErrorMessage("无效id");
    }
}
