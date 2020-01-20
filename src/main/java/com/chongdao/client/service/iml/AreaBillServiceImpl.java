package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.AreaBill;
import com.chongdao.client.entitys.Management;
import com.chongdao.client.mapper.AreaBillMapper;
import com.chongdao.client.repository.ManagementRepository;
import com.chongdao.client.service.AreaBillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/6/26
 * @Version 1.0
 **/
@Service
public class AreaBillServiceImpl implements AreaBillService {
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private AreaBillMapper areaBillMapper;

    @Override
    public ResultResponse getAreaBillByManagementId(Integer managementId, String shopName, Date startDate, Date endDate, Integer type, Integer pageNum, Integer pageSize) {
        Management management = managementRepository.findById(managementId).orElse(null);
        PageHelper.startPage(pageNum,pageSize);
        if(management != null) {
            String areaCode = management.getAreaCode();
            List<AreaBill> list = areaBillMapper.getAreaBillByAreaCodeAndType(areaCode, type, startDate, endDate);
            PageInfo<AreaBill> pageInfo = new PageInfo<>(list);
            pageInfo.setList(list);
            return ResultResponse.createBySuccess(pageInfo);
        } else {
            return ResultResponse.createByErrorMessage("错误的管理员ID!");
        }
    }

    @Override
    public ResultResponse getAreaAccountMoneyData(Integer managementId) {
        Management management = managementRepository.findById(managementId).orElse(null);
        if(management != null) {
            String areaCode = management.getAreaCode();
            BigDecimal orderInMoney = areaBillMapper.getAreaAccountMoneyData(areaCode, 1);//订单消费入账
            BigDecimal orderRefundMoney = areaBillMapper.getAreaAccountMoneyData(areaCode, 2);//订单消费退款
            BigDecimal myWithdrawalMoney = areaBillMapper.getAreaAccountMoneyData(areaCode, 3);//我的提现
            BigDecimal insuranceFeeMoney = areaBillMapper.getAreaAccountMoneyData(areaCode, 4);//医疗费用订单
            BigDecimal reOrderMoney = areaBillMapper.getAreaAccountMoneyData(areaCode, 5);//追加订单
            BigDecimal inMoney = orderInMoney.add(insuranceFeeMoney).add(reOrderMoney);
            BigDecimal outMoney = orderRefundMoney.add(myWithdrawalMoney);
            Map resp = new HashMap<>();
            resp.put("balanceMoney", inMoney.subtract(outMoney));//余额
            resp.put("outMoney", outMoney);//支出
            resp.put("inMoney", inMoney);//收入
            return ResultResponse.createBySuccess(resp);
        } else {
            return ResultResponse.createByErrorMessage("错误的管理员ID!");
        }
    }
}
