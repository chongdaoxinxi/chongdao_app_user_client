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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTransRepository userTransRepository;

    @Override
    @Transactional
    public ResultResponse customOrderCashIn(OrderInfo orderInfo) {
        BigDecimal servicePrice = conversionNullBigDecimal(orderInfo.getServicePrice());
        BigDecimal goodsPrice = conversionNullBigDecimal(orderInfo.getGoodsPrice());
        BigDecimal insurancePrice = conversionNullBigDecimal(orderInfo.getInsurancePrice());
        BigDecimal totalDiscount = conversionNullBigDecimal(orderInfo.getTotalDiscount());
        BigDecimal subtract = servicePrice.add(goodsPrice).add(insurancePrice).subtract(totalDiscount);
        //商家的钱入商家, 并生成流水记录

        //商家的钱+系统的钱 入地区账户
        String areaCode = orderInfo.getAreaCode();
        Management areaAdmin = getAreaAdmin(areaCode);
        managementMoneyDeal(areaAdmin, subtract);
        //生成流水记录
        generateAreaBill(orderInfo.getId(), orderInfo.getShopId(), areaCode, subtract, "客户订单", 1);

        //商家的钱+系统的钱 入超级账户
        Management superAdmin = getSuperAdmin();
        managementMoneyDeal(superAdmin, subtract);
        //生成流水记录
        generateSuperAdminBill(orderInfo.getId(), orderInfo.getShopId(), orderInfo.getAreaCode(), subtract, "客户订单", 1);
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse insuranceFeeCashIn(InsuranceFeeRecord insuranceFeeRecord) {
        BigDecimal money = insuranceFeeRecord.getMoney();
        BigDecimal toShopMoney = money;
        Integer type = insuranceFeeRecord.getType();//是否保险类
        Integer shopId = insuranceFeeRecord.getShopId();
        Shop shop = shopRepository.findById(shopId).orElse(null);
        //钱入商家(医院), 并生成流水记录
        if(type == 1) {
            //保险类
            //存入时扣除费用
            toShopMoney = money.multiply(new BigDecimal((100 - DeductPercentEnum.INSURANCE_FEE_DEDUCT.getCode()) / 100));
        }
        shopMoneyDeal(shop, toShopMoney);
        generateShopBill(null, shopId, money, "医疗费用订单", 4);
        //钱入地区账户, 并生成流水记录
        generateAreaBill(null, shopId, shop.getAreaCode(), money, "医疗费用订单", 4);
        Management areaAdmin = getAreaAdmin(shop.getAreaCode());
        managementMoneyDeal(areaAdmin, money);
        //钱入超级账户, 并生成流水记录
        generateSuperAdminBill(null, shopId, shop.getAreaCode(), money, "医疗费用订单", 4);
        Management superAdmin = getSuperAdmin();
        managementMoneyDeal(superAdmin, money);
        return ResultResponse.createBySuccess();
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
        Integer id = orderInfo.getId();
        //当产生退款时, 根据各账户的流水入账记录进行资金扣除
        //商家账户资金扣除, 并生成流水记录
        List<ShopBill> sbList = shopBillRepository.findByOrderIdAndPriceGreaterThan(id, new BigDecimal(0));//查询出入账记录
        if(sbList.size() > 0) {
            ShopBill inShopBill = sbList.get(0);
            Integer shopId = inShopBill.getShopId();
            Shop shop = shopRepository.findById(shopId).orElse(null);
            BigDecimal outMoney = inShopBill.getPrice().multiply(new BigDecimal(-1));
            shopMoneyDeal(shop, outMoney);
            generateShopBill(id, shopId, outMoney, "订单退款", 2);
        }
        //从地区账户资金扣除, 并生成流水记录
        List<AreaBill> abList = areaBillRepository.findByOrderIdAndType(id, 1);//查询出入账记录
        if(abList.size() > 0) {
            AreaBill inAreaBill = abList.get(0);
            BigDecimal outMoney = inAreaBill.getPrice().multiply(new BigDecimal(-1));
            String areaCode = inAreaBill.getAreaCode();
            Management areaAdmin = getAreaAdmin(areaCode);
            managementMoneyDeal(areaAdmin, outMoney);
            generateAreaBill(id, inAreaBill.getShopId(), areaCode, outMoney, "订单退款", 2);
        }
        //从超级账户资金扣除, 并生成流水记录
        List<SuperAdminBill> sabList = superAdminBillRepository.findByOrderIdAndType(id, 1);
        if(sabList.size() > 0) {
            SuperAdminBill inSuperAdminBill = new SuperAdminBill();
            BigDecimal outMoney = inSuperAdminBill.getPrice().multiply(new BigDecimal(-1));
            Management superAdmin = getSuperAdmin();
            managementMoneyDeal(superAdmin, outMoney);
            generateSuperAdminBill(id, inSuperAdminBill.getShopId(), inSuperAdminBill.getAreaCode(), outMoney, "订单退款", 2);
        }
        return ResultResponse.createBySuccess();
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
        BigDecimal money = conversionNullBigDecimal(userWithdrawal.getMoney());
        Integer userId = userWithdrawal.getUserId();
        BigDecimal realMoney = conversionNullBigDecimal(userWithdrawal.getRealMoney());
        User user = userRepository.findById(userId).orElse(null);
        userMoneyDeal(user, money);
        generateUserTrans(null, userId, "用户提现", realMoney, 7);
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse shopWithdrawal(ShopApply shopApply) {
        BigDecimal applyMoney = conversionNullBigDecimal(shopApply.getApplyMoney());
        BigDecimal realMoney = conversionNullBigDecimal(shopApply.getRealMoney());
        Integer shopId = shopApply.getShopId();
        Shop shop = shopRepository.findById(shopId).orElse(null);
        shopMoneyDeal(shop, applyMoney);
        generateShopBill(null, shopId, realMoney, "店铺提现", 3);
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse areaAdminWithdrawal(AreaWithdrawalApply areaWithdrawalApply) {
        BigDecimal applyMoney = conversionNullBigDecimal(areaWithdrawalApply.getApplyMoney());
        BigDecimal realMoney = conversionNullBigDecimal(areaWithdrawalApply.getRealMoney());
        Integer managementId = areaWithdrawalApply.getManagementId();
        Management management = managementRepository.findById(managementId).orElse(null);
        managementMoneyDeal(management, applyMoney);
        generateAreaBill(null, null, management.getAreaCode(), realMoney, "地区账户提现", null);
        return ResultResponse.createBySuccess();
    }

    @Override
    public ResultResponse insuranceAdminWithdrawal() {
        return null;
    }


    /**
     * 用户资金出入账
     * @param u
     * @param money
     */
    private void userMoneyDeal(User u, BigDecimal money) {
        BigDecimal oldMoney = conversionNullBigDecimal(u.getMoney());
        u.setMoney(oldMoney.add(money));
        userRepository.save(u);
    }

    /**
     * 商家资金出入账
     */
    private void shopMoneyDeal(Shop s, BigDecimal money) {
        BigDecimal oldMoney = conversionNullBigDecimal(s.getMoney());
        s.setMoney(oldMoney.add(money));
        shopRepository.save(s);
    }

    /**
     * 获取商家实际应该的该入账资金
     * @return
     */
    private BigDecimal getShopRealInMoney() {
        return null;
    }

    /**
     * 区域账户资金出入账/超级管理员资金出入账
     */
    private void managementMoneyDeal(Management m, BigDecimal money) {
        BigDecimal oldMoney = conversionNullBigDecimal(m.getMoney());
        m.setMoney(oldMoney.add(money));
        managementRepository.save(m);
    }

    /**
     * 生成用户资金流水日志
     * @param orderId
     * @param userId
     * @param comment
     * @param money
     * @param type
     */
    private void generateUserTrans(Integer orderId, Integer userId, String comment, BigDecimal money, Integer type) {
        UserTrans ut = new UserTrans();
        ut.setOrderId(orderId);
        ut.setUserId(userId);
        ut.setComment(comment);
        ut.setMoney(money);
        ut.setType(type);
        userTransRepository.save(ut);
    }

    /**
     * 生成商家流水日志
     * @param orderId
     * @param shopId
     * @param price
     * @param note
     * @param type
     */
    private void generateShopBill(Integer orderId, Integer shopId, BigDecimal price, String note, Integer type) {
        ShopBill sb = new ShopBill();
        sb.setOrderId(orderId);
        sb.setShopId(shopId);
        sb.setPrice(price);
        sb.setNote(note);
        sb.setType(type);
        shopBillRepository.save(sb);
    }

    /**
     * 生成区域账户流水日志
     * @param orderId
     * @param shopId
     * @param areaCode
     * @param price
     * @param note
     * @param type
     */
    private void generateAreaBill(Integer orderId, Integer shopId, String areaCode, BigDecimal price, String note, Integer type) {
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

    /**
     * 生成超级管理账户流水日志
     * @param orderId
     * @param shopId
     * @param areaCode
     * @param price
     * @param note
     * @param type
     */
    private void generateSuperAdminBill(Integer orderId, Integer shopId, String areaCode, BigDecimal price, String note, Integer type) {
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
