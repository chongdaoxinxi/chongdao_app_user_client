package com.chongdao.client.service.insurance;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.InsuranceOrder;
import com.chongdao.client.entitys.OrderInfo;

import java.io.IOException;
import java.util.Date;

public interface InsuranceService {
    ResultResponse saveInsurance(InsuranceOrder insuranceOrder) throws IOException;

    /**
     * 根据配送订单投保运输险
     * @param orderInfo
     * @return
     * @throws IOException
     */
    ResultResponse insuranceZcg(OrderInfo orderInfo) throws IOException;

    ResultResponse getMyInsuranceData(String token, Integer pageSize, Integer pageNum);

    ResultResponse getInsuranceDetail(Integer insuranceId);

    ResultResponse downloadElectronicInsurancePolicy(Integer insuranceId);

    ResultResponse getInsuranceDataList(String token, Integer insuranceType, String userName, String phone, String insuranceOrderNo, Date start, Date end, Integer status, Integer pageNum, Integer pageSize);

    ResultResponse auditInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note);

    ResultResponse refuseInsurance(String token, Integer insuranceOrderId, Integer targetStatus, String note);

    ResultResponse pollingCheckOrderStatus(Integer insuranceOrderId);

    ResultResponse getInsuranceUserTodo(String token);

    ResultResponse getEffectedInsuranceOrderByUserId(Integer userId);

    ResultResponse getMyOrderIsBuyInsurance(String orderNo);

    boolean IsBuyInsurance(String orderNo);

    ResultResponse getMyPickupInsuranceOrderList(String orderNo);

    ResultResponse repairPickupOrder();
}
