package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.DicInfo;
import com.chongdao.client.repository.DicInfoRepository;
import com.chongdao.client.repository.OrderInfoRepository;
import com.chongdao.client.service.ServiceRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/12/12
 * @Version 1.0
 **/
@Service
public class ServiceRuleServiceImpl implements ServiceRuleService {
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private DicInfoRepository dicInfoRepository;

    @Override
    public ResultResponse getServiceRuleInfo(BigDecimal originServicePrice, Integer serviceType, Integer isService, String areaCode, BigDecimal serviceDistance) {
        if (StringUtils.isNotBlank(areaCode) && (areaCode.equals("3101") == false) && (areaCode.equals("5201") == false)) {
            //非上海/贵阳地区 配送规则按上海计算
            areaCode = "3101";
        }
        Map map = new HashMap<>();
        if (areaCode.equals("3101")) {
                DicInfo twicerateDicInfo = dicInfoRepository.findByAreaCodeAndCodeAndStatus(areaCode, "twicerate", 1);
                DicInfo singleDicInfo = dicInfoRepository.findByAreaCodeAndCodeAndStatus(areaCode, "singlerate", 1);
                DicInfo goodDicInfo = dicInfoRepository.findByAreaCodeAndCodeAndStatus(areaCode, "goodslerate", 1);
                setConfigData(twicerateDicInfo, singleDicInfo, goodDicInfo, map);
        } else if (areaCode.equals("5201")) {
                DicInfo twicerateDicInfo = dicInfoRepository.findByAreaCodeAndCodeAndStatus(areaCode, "twicerate_5201", 1);
                DicInfo singleDicInfo = dicInfoRepository.findByAreaCodeAndCodeAndStatus(areaCode, "singlerate_5201", 1);
                DicInfo goodDicInfo = dicInfoRepository.findByAreaCodeAndCodeAndStatus(areaCode, "goodslerate_5201", 1);
                setConfigData(twicerateDicInfo, singleDicInfo, goodDicInfo, map);
        }
        //订单部分
        if(serviceType == 1) {
            //双程
            map.put("orderStart", map.get("twiceStart"));
            map.put("orderName", "双程服务订单");
        } else if(serviceType == 2) {
            //单程
            if(isService == 1) {
                //服务
                map.put("orderStart", map.get("singleStart"));
                map.put("orderName", "单程服务订单");
            } else {
                //商品
                map.put("orderStart", map.get("goodStart"));
                map.put("orderName", "单程商品订单");
            }
        } else if(serviceType == 3) {
            //到店
            map.put("orderStart", 0);
            map.put("orderName", "单程到店订单");
        }
        BigDecimal withinFee = originServicePrice.subtract(new BigDecimal((String) map.get("orderStart")));
        map.put("withinFee", withinFee);
        map.put("originServicePrice", originServicePrice);
        map.put("serviceDistance", serviceDistance);
        return ResultResponse.createBySuccess(map);
    }

    /**
     * 设置配置部分数据
     * @param twicerateDicInfo - 双程配置
     * @param singleDicInfo - 单程配置
     * @param map
     */
    private void setConfigData(DicInfo twicerateDicInfo, DicInfo singleDicInfo, DicInfo goodDicInfo, Map map) {
        String twicerateVal = twicerateDicInfo.getVal();
        List<String> twicerateList = getDetailDicInfoData(twicerateVal);
        map.put("twiceStartLimitDistance", (Integer.valueOf(twicerateList.get(0)))/1000);//双程起步公里
        map.put("twiceStart", twicerateList.get(1));//双程起步价
        map.put("twiceWithin10", twicerateList.get(2));//双程10公里内单价
        map.put("twiceOut10", twicerateList.get(3));//双程10公里外单价
        String singleVal = singleDicInfo.getVal();
        List<String> singleList = getDetailDicInfoData(singleVal);
        map.put("twiceStartLimitDistance", (Integer.valueOf(singleList.get(0)))/1000);//单程起步公里
        map.put("singleStart", singleList.get(1));//单程起步价
        map.put("singleWithin10", singleList.get(2));//单程10公里内起步价
        map.put("singleOut10", singleList.get(3));//单程10公里外单价
        String goodVal = goodDicInfo.getVal();
        List<String> goodList = getDetailDicInfoData(goodVal);
        map.put("goodStartLimitDistance", (Integer.valueOf(goodList.get(0)))/1000);//商品类订单起步公里
        map.put("goodStart", goodList.get(1));//商品类起步价
        map.put("goodWithin10", goodList.get(2));//商品类10公里内起步价
        map.put("goodOut10", goodList.get(3));//商品类10公里外单价
    }

    private List<String> getDetailDicInfoData(String val) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(val)) {
            String[] arrs = val.split("_");
            for (String str : arrs) {
                list.add(str);
            }
        }
        return list;
    }
}
