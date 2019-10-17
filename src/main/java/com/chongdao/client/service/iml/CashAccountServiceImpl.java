package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.*;
import com.chongdao.client.enums.DeductPercentEnum;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.CashAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/10/17
 * @Version 1.0
 **/
@Service
public class CashAccountServiceImpl implements CashAccountService {
    @Autowired
    private ManagementRepository managementRepository;
    @Autowired
    private SuperAdminBillRepository superAdminBillRepository;
    @Autowired
    private AreaBillRepository areaBillRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopBillRepository shopBillRepository;

    @Override
    @Transactional
    public ResultResponse customOrderCashIn(OrderInfo orderInfo) {
        BigDecimal servicePrice = conversionNullBigDecimal(orderInfo.getServicePrice());
        BigDecimal goodsPrice = conversionNullBigDecimal(orderInfo.getGoodsPrice());
        BigDecimal insurancePrice = conversionNullBigDecimal(orderInfo.getInsurancePrice());
        BigDecimal totalDiscount = conversionNullBigDecimal(orderInfo.getTotalDiscount());
        //商家的钱入商家, 并生成流水记录

        //商家的钱+系统的钱 入地区账户
        String areaCode = orderInfo.getAreaCode();
        BigDecimal subtract = servicePrice.add(goodsPrice).add(insurancePrice).subtract(totalDiscount);
        Management areaAdmin = getAreaAdmin(areaCode);
        BigDecimal areaMoney = conversionNullBigDecimal(areaAdmin.getMoney());
        areaAdmin.setMoney(areaMoney.add(subtract));
        managementRepository.save(areaAdmin);
        //生成流水记录
        genarateAreaBill(orderInfo.getId(), orderInfo.getShopId(), areaCode, subtract, "客户订单", 1);

        //商家的钱+系统的钱 入超级账户
        Management superAdmin = getSuperAdmin();
        BigDecimal money = conversionNullBigDecimal(superAdmin.getMoney());
        superAdmin.setMoney(money.add(subtract));
        managementRepository.save(superAdmin);
        //生成流水记录
        genarateSuperAdminBill(orderInfo.getId(), orderInfo.getShopId(), orderInfo.getAreaCode(), subtract, "客户订单", 1);
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse insuranceFeeCashIn(InsuranceFeeRecord insuranceFeeRecord) {
        BigDecimal money = insuranceFeeRecord.getMoney();
        Integer type = insuranceFeeRecord.getType();//是否保险类
        Integer shopId = insuranceFeeRecord.getShopId();
        Shop shop = shopRepository.findById(shopId).orElse(null);
        BigDecimal shopMoney = conversionNullBigDecimal(shop.getMoney());
        BigDecimal insuranceMoney = conversionNullBigDecimal(shop.getInsuranceMoney());
        //钱入商家(医院), 并生成流水记录
        if(type == 1) {
            //保险类
            //存入时扣除费用
            money = money.multiply(new BigDecimal((100 - DeductPercentEnum.INSURANCE_FEE_DEDUCT.getCode()) / 100));
        }
        shop.setMoney(shopMoney.add(money));
        shop.setInsuranceMoney(insuranceMoney.add(money));
        shopRepository.save(shop);
        ShopBill sb = new ShopBill();
        sb.setShopId(shopId);
        sb.setNote("医疗费用订单");
        sb.setPrice(money);
        sb.setType(4);
        sb.setCreateTime(new Date());
        shopBillRepository.save(sb);

        //钱入地区账户, 并生成流水记录

        //钱入超级账户, 并生成流水记录

        return null;
    }

    @Override
    public ResultResponse petPickupOrderCashIn(OrderInfo orderInfo) {
        return null;
    }

    @Override
    public ResultResponse couponCashIn(Coupon coupon) {
        return null;
    }

    @Override
    public ResultResponse customOrderCashRefund(OrderInfo orderInfo) {
        return null;
    }

    @Override
    public ResultResponse insuranceFeeCashRefund(InsuranceFeeRecord insuranceFeeRecord) {
        return null;
    }

    @Override
    public ResultResponse newUserReward(OrderInfo orderInfo) {
        return null;
    }

    @Override
    public ResultResponse insuranceOrderRecommendReward(InsuranceOrder insuranceOrder) {
        return null;
    }

    @Override
    public ResultResponse userWithdrawal(UserWithdrawal userWithdrawal) {
        return null;
    }

    @Override
    public ResultResponse shopWithdrawal(ShopApply shopApply) {
        return null;
    }

    @Override
    public ResultResponse areaAdminWithdrawal(AreaWithdrawalApply areaWithdrawalApply) {
        return null;
    }

    @Override
    public ResultResponse insuranceAdminWithdrawal() {
        return null;
    }

    private void genarateAreaBill(Integer orderId, Integer shopId, String areaCode, BigDecimal price, String note, Integer type) {
        //生成流水记录
        AreaBill ab = new AreaBill();
        ab.setOrderId(orderId);
        ab.setShopId(shopId);
        ab.setNote(note);
        ab.setType(type);
        ab.setPrice(price);
        ab.setCreateTime(new Date());
        ab.setAreaCode(areaCode);
        areaBillRepository.save(ab);
    }

    private void genarateSuperAdminBill(Integer orderId, Integer shopId, String areaCode, BigDecimal price, String note, Integer type) {
        //生成流水记录
        SuperAdminBill sab = new SuperAdminBill();
        sab.setOrderId(orderId);
        sab.setShopId(shopId);
        sab.setNote(note);
        sab.setType(type);
        sab.setPrice(price);
        sab.setAreaCode(areaCode);
        sab.setCreateTime(new Date());
        superAdminBillRepository.save(sab);
    }

    private Management getAreaAdmin(String areaCode) {
        List<Management> list = managementRepository.findByAreaCodeAndStatus(areaCode, 1);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private Management getSuperAdmin() {
        List<Management> list = managementRepository.findByLevel(99);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private BigDecimal conversionNullBigDecimal(BigDecimal d) {
        if (d == null) {
            return new BigDecimal(0);
        } else {
            return d;
        }
    }
}
