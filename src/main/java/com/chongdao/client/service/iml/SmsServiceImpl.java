package com.chongdao.client.service.iml;

import com.chongdao.client.common.GuavaCache;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Express;
import com.chongdao.client.entitys.OrderAddress;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.utils.sms.SMSUtil;
import com.chongdao.client.utils.sms.SmsSender253;
import com.chongdao.client.utils.sms.SmsVariableRequest;
import com.chongdao.client.utils.sms.SmsVariableResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SMSUtil smsUtil;

    private static final Random random = new Random();
    private static final String[] NUMS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final static String SMS_CODE_CONTENT_PREFIX = "SMS::CODE::CONTENT";

    @Autowired
    private Gson gson;

    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private ShopRepository shopRespository;
    @Autowired
    private ExpressRepository expressRepository;
    @Autowired
    private DicInfoRepository dicInfoRepository;
    @Autowired
    private OrderAddressRepository orderAddressRepository;

    /**
     * 发送验证码到指定手机 并 缓存验证码 10分钟 及 请求间隔时间1分钟
     * @param telephone
     * @return
     */
    @Override
    public ResultResponse<String> sendSms(String telephone) {
        String gapKey = "SMS::CODE::INTERVAL::" + telephone;
 /*       String result = GuavaCache.getKey(gapKey);
        if (result != null) {
            return  ResultResponse.createByErrorMessage( "请求次数太频繁");
        }*/
        //生成验证码
        String code = generateRandomSmsCode();
        String params = telephone+","+code;

        String report= "true";

        SmsVariableRequest smsVariableRequest=new SmsVariableRequest(this.smsUtil.getAccount(), this.smsUtil.getPassword(),
                this.smsUtil.getSmsIdentifyCode(), params, report);

        String requestJson = gson.toJson(smsVariableRequest);

        String response = SmsSender253.sendSmsByPost(this.smsUtil.getUrl(), requestJson);

        SmsVariableResponse resp = gson.fromJson(response, SmsVariableResponse.class);
        boolean success = false;
        //请求成功
        if ("0".equals(resp.getCode())){
            success= true;
        }
        if (success) {
            //将验证码key存入guava缓存中
            GuavaCache.setKey(gapKey, code);
            GuavaCache.setKey(SMS_CODE_CONTENT_PREFIX + telephone, code);
            return ResultResponse.createBySuccess(code);
        } else {
            throw new PetException(ResultEnum.ERROR);
        }
    }

    /**
     * 获取缓存中的验证码
     * @param telephone
     * @return
     */
    @Override
    public String getSmsCode(String telephone) {
        return GuavaCache.getKey(SMS_CODE_CONTENT_PREFIX + telephone);
    }

    /**
     * 移除指定手机号的验证码缓存
     */
    @Override
    public void remove(String telephone) {

    }

    /**
     * 6位验证码生成器
     * @return
     */
    private static String generateRandomSmsCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(10);
            sb.append(NUMS[index]);
        }
        return sb.toString();
    }


    ////////////////////////////订单流程短信服务///////////////////////////////////////////////


    @Override
    public void acceptOrderMsgUserSender(String orderNo, String shopName, List<String> phoneList) {
        //TODO 发送给用户的短信应该是发什么号码呢?orderAddress里的?接/送地址都发?还是用户表里的?
    }

    @Override
    public void acceptOrderMsgExpressSender(String orderNo, String shopName, List<String> phoneList) {
        String params = assemblePhoneList(orderNo, shopName, phoneList);
        String report= "true";
        customMsgSender(this.smsUtil.getExpressNewOrder(), params, report);
    }

    @Override
    public void acceptOrderMsgShopSender(String orderNo, String shopName, String telephone) {
        String params = telephone+","+ orderNo + "," + shopName;
        String report= "true";
        customMsgSender(this.smsUtil.getShopAcceptRefund(), params, report);
    }

    @Override
    public void refundOrderMsgAdminSender(String orderNo, String shopName, List<String> phoneList) {
        String params = assemblePhoneList(orderNo, shopName, phoneList);
        String report= "true";
        customMsgSender(this.smsUtil.getShopAgreeRefundOrder(), params, report);
    }

    @Override
    public void refuseOrderMsgUserSender(String orderNo, String shopName, String telephone) {
        String params = telephone+","+ shopName + "," + orderNo;
        String report= "true";
        customMsgSender(this.smsUtil.getShopRefuseOrder(), params, report);
    }

    @Override
    public void serviceCompleteMsgExpressSender(String orderNo, String shopName, String telephone) {
        String params = telephone+","+ shopName + "," + orderNo;
        String report= "true";
        customMsgSender(this.smsUtil.getExpressServiceComplete(), params, report);
    }

    @Override
    public void serviceCompleteMsgUserSender(String orderNo, String shopName, List<String> phoneList) {
        //TODO 发送给用户的短信应该是发什么号码呢?orderAddress里的?接/送地址都发?还是用户表里的?
    }

    /**
     * 封装联系电话 list->params
     * @param orderNo
     * @param shopName
     * @param phoneList
     * @return
     */
    private String assemblePhoneList(String orderNo, String shopName, List<String> phoneList) {
        String params = "";
        for (int i = 0; i < phoneList.size(); i ++) {
            params = params + phoneList.get(i) + "," + shopName +","+ orderNo;
            if (i < phoneList.size() - 1) {
                params = params + ";";
            }
        }
        return params;
    }

    /**
     * 标准发送短信模式
     * @param msg
     * @param params
     * @param report
     */
    private void customMsgSender(String msg, String params, String report) {
        SmsVariableRequest smsVariableRequest=new SmsVariableRequest(this.smsUtil.getAccount(), this.smsUtil.getPassword(),
                msg, params, report);
        String requestJson = gson.toJson(smsVariableRequest);
        String response = SmsSender253.sendSmsByPost(this.smsUtil.getUrl(), requestJson);
        gson.fromJson(response, SmsVariableResponse.class);
    }

    @Override
    public List<String> getExpressPhoneListByOrderId(Integer orderId) {
        List<String> resp = new ArrayList<>();
        Optional.ofNullable(orderId)
                .flatMap(id -> orderInfoRepository.findById(id))
                .map(o -> o.getAreaCode())
                .map(code -> expressRepository.findByAreaCodeAndStatus(code, 1))
                .map(list -> {
                    list.forEach(e -> Optional.ofNullable(e).map(e1 -> e1.getPhone()).map(phone -> resp.add(phone)));
                    return resp;
                })
                .orElse(resp);
        return resp;
    }

    @Override
    public String getExpressPhoneByOrderId(Integer orderId) {
        return Optional.ofNullable(orderId)
                .flatMap(id -> orderInfoRepository.findById(id))
                .map(o -> o.getExpressId())
                .map(epId -> expressRepository.findByIdAndStatus(epId, 1))
                .map(list -> {
                    String phone = "";
                    for (Express express : list) {
                        phone = Optional.ofNullable(express).map(e -> e.getPhone()).orElse("");
                    }
                    return phone;
                }).orElse("");
    }

    @Override
    public List<String> getAdminPhoneListByOrderId(Integer orderId) {
        List<String> resp = new ArrayList<>();
        Optional.ofNullable(orderId)
                .flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> o.getAreaCode())
                .flatMap(code -> dicInfoRepository.findByCodeAndAreaCodeAndStatus("admin_phone", code, 1))
                .map(list -> {
                    list.forEach(e -> Optional.ofNullable(e).map(e1 -> e1.getVal()).map(v -> resp.add(v)));
                    return resp;
                })
                .orElse(resp);
        return resp;
    }

    @Override
    public Map<String, String> getShopParamsByOrderId(Integer orderId) {
        Map<String, String> resp = new HashMap<>();
        Optional.ofNullable(orderId)
                .flatMap(id -> orderInfoRepository.findById(id))
                .map(o -> o.getShopId())
                .flatMap(sId -> shopRespository.findById(sId))
                .flatMap(s -> Optional.ofNullable(s.getShopName()).map(sName -> {
                    resp.put("shopName", sName);
                    return s;
                }))
                .flatMap(s -> Optional.ofNullable(s.getPhone()).map(phone -> {
                    resp.put("shopPhone", phone);
                    return s;
                })).orElse(null);
        return resp;
    }

    @Override
    public Map<String, String> getUserParamsByOrderId(Integer orderId) {
        Map<String, String> resp = new HashMap<>();
        if(orderId != null) {
            List<OrderAddress> oaList = orderAddressRepository.findByOrderId(orderId);
            oaList.forEach(e -> {
                Integer type = e.getType();
                if(type == 1) {
                    //接宠
                    resp.put("greetUserName", e.getUserName());
                    resp.put("greetUserPhone", e.getPhone());
                } else if(type == 2){
                    //送宠
                    resp.put("sendUserName", e.getUserName());
                    resp.put("greetUserPhone", e.getPhone());
                }
            });
        }
        return resp;
    }
}
