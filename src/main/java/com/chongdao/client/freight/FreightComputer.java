package com.chongdao.client.freight;

import com.chongdao.client.entitys.DicInfo;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.entitys.UserAddress;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.repository.DicInfoRepository;
import com.chongdao.client.repository.ShopRepository;
import com.chongdao.client.repository.UserAddressRepository;
import com.chongdao.client.utils.DistanceUtil;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fenglong
 * @date 2019-08-02 12:26
 * 运费计算
 */
@Component
public class FreightComputer {

    //贵阳单程计算 （3公里以内双程配送费10元,超过3公里后每公里以3元计算,大于10公里后每公里以6元计算）
    private static final String GY_SINGLE_3KM_FEE  = "singlerate_5201";
    //贵阳双程计算 （3公里以内双程配送费15元,超过3公里后每公里以3元计算,大于10公里后每公里以6元计算）
    private static final String GY_TWICE_3KM_FEE  = "twicerate_5201";

    //上海区域 3公里以内单程配送费15元,超过3公里后每公里加4.5元 大于10公里后每公里以6元计算
    //3公里以内双程配送费30元,同上
    //3公里以内单程配送费10元,超过3公里后每公里加4.5元
    private static final String SH_SINGLE_3KM_FEE  = "singlerate";
    private static final String SH_TWICE_3KM_FEE  = "twicerate";
    private static final String GOODS_FEE = "goodslerate";

    private static final Double RANGE_10_KM = 10000.0D;

    private static final Double RANGE_3_KM = 3000.0D;

    private static final Double OVERSTEP_10KM_PRICE = 5.0D;

    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private DicInfoRepository dicInfoRepository;

    /**
     * 运费计算
     * @param serviceType 服务类型 1.双程 2.单程 3.到店自取
     * @param isService 0.商品 1.服务
     * @param receiveAddressId 接地址id
     * @param deliverAddressId 送地址id
     * @param shopId  商店id
     * @return
     */
    public BigDecimal computerFee(Integer serviceType,Integer isService, Integer receiveAddressId, Integer deliverAddressId,
                                  Integer shopId,Integer userId){
        //查询用户接地址
        UserAddress receiveUserAddress = userAddressRepository.findByIdAndUserId(receiveAddressId, userId);
        //查询商铺信息
        Shop shop = shopRepository.findById(shopId).get();
        if (receiveUserAddress == null || shop == null){
            throw new PetException(ResultEnum.PARAM_ERROR.getStatus(),"地址或店铺为空");
        }
        //根据code码以及区域码区分地区配送费计算方式
        String areaCode = shop.getAreaCode();
        String code = this.getCode(serviceType, areaCode, isService);
        DicInfo dicInfo = dicInfoRepository.findByAreaCodeAndCodeAndStatus(shop.getAreaCode(), code, 1);
        List<String> firstServiceMoneys = Splitter.on("_").trimResults().omitEmptyStrings().splitToList(dicInfo.getVal());
        //起步公里数 3km
        Double baseDistance = Double.valueOf(firstServiceMoneys.get(0));
        //起步价
        Double baseMoney = Double.valueOf(firstServiceMoneys.get(1));
        //接地址 到 商家的距离
        Double receiveDistance = DistanceUtil.getDistance(receiveUserAddress.getLat(), receiveUserAddress.getLng(), shop.getLat(), shop.getLng());
        Double deliverDistance = 0.0d;
        //服务类型为双程时，送地址不为空
        UserAddress deliverAddress = null;
        if (1 == serviceType) {
            deliverAddress = userAddressRepository.findByIdAndUserId(receiveAddressId, userId);
            deliverDistance = DistanceUtil.getDistance(deliverAddress.getLat(), deliverAddress.getLng(), shop.getLat(), shop.getLng());
        }
        //超出三公里的费用
        Double overMoneyBase = this.getOverMoneys(serviceType,receiveDistance,deliverDistance,firstServiceMoneys);
        return new BigDecimal(baseMoney+this.computerFinalFee(serviceType,receiveDistance,deliverDistance,
                firstServiceMoneys,overMoneyBase,baseDistance,areaCode));

    }

    /**
     * 获取运费码
     * @param serviceType
     * @param areaCode
     * @param isService
     * @return
     */
    private String getCode(Integer serviceType,String areaCode,Integer isService){
        String code = "";
        if (1 == serviceType && areaCode.equals("3101") && 1 == isService ){
            code = SH_TWICE_3KM_FEE;
        }else if (1 == serviceType && areaCode.equals("5201") && 1 == isService ){
            code = GY_TWICE_3KM_FEE;
        }else if (2 == serviceType && areaCode.equals("3101") && 1 == isService ){
            code = SH_SINGLE_3KM_FEE;
        }else if (2 == serviceType && areaCode.equals("5201") && 1 == isService){
            code = GY_SINGLE_3KM_FEE;
        }else{
            code = GOODS_FEE;
        }
        return code;
    }

    /**
     * 获取超出三公里外的起步价格
     * @param serviceType
     * @param receiveDistance
     * @param deliverDistance
     * @param firstServiceMoneys
     * @return
     */
    private Double getOverMoneys(Integer serviceType, Double receiveDistance,Double deliverDistance,List<String> firstServiceMoneys ){
        Double overMoneyBase = 0.0D;
        if (1 == serviceType){
            //超出三公里 小于10公里
            if ((receiveDistance > RANGE_3_KM && receiveDistance <= RANGE_10_KM) && (deliverDistance > RANGE_3_KM && deliverDistance <= RANGE_10_KM)){
                overMoneyBase = Double.valueOf(firstServiceMoneys.get(2));
            }else if (receiveDistance <= RANGE_3_KM || deliverDistance <= RANGE_3_KM ){
                overMoneyBase = 0.0d;
            }else{
                overMoneyBase = OVERSTEP_10KM_PRICE;
            }
        }else if (2 == serviceType){
            if (receiveDistance > RANGE_3_KM && receiveDistance <= RANGE_10_KM){
                overMoneyBase = Double.valueOf(firstServiceMoneys.get(2));
            }else if (receiveDistance <= RANGE_3_KM){
                overMoneyBase = 0.0d;
            }else{
                overMoneyBase = OVERSTEP_10KM_PRICE;
            }
        }
        return overMoneyBase;
    }


    /**
     * 计算超出的价格
     * @param serviceType
     * @param receiveDistance
     * @param deliverDistance
     * @param firstServiceMoneys
     * @param overMoneyBase
     * @param baseDistance
     * @param areaCode
     * @return
     */
    private Double computerFinalFee(Integer serviceType, Double receiveDistance,Double deliverDistance,
                                    List<String> firstServiceMoneys,Double overMoneyBase,Double baseDistance,String areaCode) {
        Double overMoney = 0.0D;
        //超出基础距离三公里
        double overBaseDistance = Math.ceil((receiveDistance - baseDistance) / 1000.0D) * overMoneyBase;
        //接送地址不一致 取最长距离(双程)
        double overDeliverDistance = 0.0d;
        if (1 == serviceType) {
            overDeliverDistance = Math.ceil((deliverDistance - baseDistance) / 1000.0D) * overMoneyBase;
        }
        //接送地址不一致时，选择最长距离计算价格
        if (deliverDistance > receiveDistance) {
            overMoney = overDeliverDistance;
        } else if ( receiveDistance > Double.valueOf(firstServiceMoneys.get(0)) || receiveDistance > deliverDistance) {
            //接地址距离大于送地址距离或者接地址距离商家超出三公里
            overMoney = overBaseDistance;
        } else {
            //小于三公里的费用
            overMoney = Double.valueOf(firstServiceMoneys.get(0));
        }
        if (areaCode.equals("3101") && serviceType != 2) {
            overMoney = overMoney * 1.5;
        }
        return overMoney;
    }

}