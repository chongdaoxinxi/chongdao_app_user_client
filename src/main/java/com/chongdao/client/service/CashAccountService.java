package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;

/**
 * 各账户资金流转
 */
public interface CashAccountService {
    /**
     * 常规订单入账
     * @return
     */
    ResultResponse customOrderCashIn(OrderInfo orderInfo);

    /**
     * 医疗费用订单入账
     * @return
     */
    ResultResponse insuranceFeeCashIn(InsuranceFeeRecord insuranceFeeRecord);

    /**
     * 运输险费用入账
     * @return
     */
    ResultResponse petPickupOrderCashIn(OrderInfo orderInfo);

    /**
     * 礼包类入账
     * @return
     */
    ResultResponse couponCashIn(Coupon coupon);

    /**
     * 常规订单退款
     * @return
     */
    ResultResponse customOrderCashRefund(OrderInfo orderInfo);

    /**
     * 医疗费用订单退款
     * @return
     */
    ResultResponse insuranceFeeCashRefund(InsuranceFeeRecord insuranceFeeRecord);

    /**
     * 返利
     * @return
     */
    ResultResponse recommendCashIn(RecommendRecord recommendRecord);

    /**
     * 用户提现
     * @return
     */
    ResultResponse userWithdrawal(UserWithdrawal userWithdrawal, Boolean isFail);

    /**
     * 商家提现
     * @return
     */
    ResultResponse shopWithdrawal(ShopApply shopApply, Boolean isFail);

    /**
     * 地区账户提现
     * @return
     */
    ResultResponse areaAdminWithdrawal(AreaWithdrawalApply areaWithdrawalApply, Boolean isFail);

    /**
     * 保险账户提现
     * @return
     */
    ResultResponse insuranceAdminWithdrawal();
}
