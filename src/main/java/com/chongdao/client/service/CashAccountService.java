package com.chongdao.client.service;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;

/**
 * 各账户资金流转
 */
public interface CashAccountService {
    /**
     * 常规订单付款后入账(常规订单付款后调用),  用户付款后先将资金转入地区账户和超级账户
     * @param orderInfo
     * @return
     */
    ResultResponse customOrderPayedCashIn(OrderInfo orderInfo);

    /**
     * 常规订单入账(常规订单商家接单后调用), 商家接单后再将商家应得的资金转入商家账户
     * @return
     */
    ResultResponse customOrderCashIn(OrderInfo orderInfo);

    /**
     * 追加订单入账(追加订单支付后调用), 资金直接转入商家、地区账户及超级账户
     * @param orderInfoRe
     * @return
     */
    ResultResponse customReOrderCashIn(OrderInfoRe orderInfoRe);

    /**
     * 医疗费用订单入账(用户付款医疗费用订单回调里调用)
     * @return
     */
    ResultResponse insuranceFeeCashIn(InsuranceFeeRecord insuranceFeeRecord);

    /**
     * 运输险费用入账
     * @return
     */
    ResultResponse petPickupOrderCashIn(OrderInfo orderInfo);

    /**
     * 礼包类入账)
     * @return
     */
    ResultResponse couponCashIn(Coupon coupon);

    /**
     * 常规订单退款(在管理点击退款完成时调用)
     * @return
     */
    ResultResponse customOrderCashRefund(OrderInfo orderInfo);

    /**
     * 医疗费用订单退款
     * @return
     */
    ResultResponse insuranceFeeCashRefund(InsuranceFeeRecord insuranceFeeRecord);

    /**
     * 返利(在返利接口逻辑最后时调用)
     * @return
     */
    ResultResponse recommendCashIn(RecommendRecord recommendRecord);

    /**
     * 用户提现(在用户账户申请提现时调用, 以及拒绝这个申请时调用)
     * @return
     */
    ResultResponse userWithdrawal(UserWithdrawal userWithdrawal, Boolean isFail);

    /**
     * 商家提现(在商家账户申请提现时调用, 以及拒绝这个申请时调用)
     * @return
     */
    ResultResponse shopWithdrawal(ShopApply shopApply, Boolean isFail);

    /**
     * 地区账户提现(在地区账户申请提现时调用, 以及拒绝这个申请时调用)
     * @return
     */
    ResultResponse areaAdminWithdrawal(AreaWithdrawalApply areaWithdrawalApply, Boolean isFail);

    /**
     * 保险账户提现
     * @return
     */
    ResultResponse insuranceAdminWithdrawal();
}
