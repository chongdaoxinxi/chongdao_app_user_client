package com.chongdao.client.service.iml;

import com.chongdao.client.common.GuavaCache;
import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Express;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.repository.*;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.utils.sms.SMSUtil;
import com.chongdao.client.utils.sms.SmsSender253;
import com.chongdao.client.utils.sms.SmsVariableRequest;
import com.chongdao.client.utils.sms.SmsVariableResponse;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    private ShopRepository shopRepository;
    @Autowired
    private ExpressRepository expressRepository;
    @Autowired
    private DicInfoRepository dicInfoRepository;
    @Autowired
    private OrderAddressRepository orderAddressRepository;

    /**
     * 发送验证码到指定手机 并 缓存验证码 10分钟 及 请求间隔时间1分钟
     *
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
        String params = telephone + "," + code;

        String report = "true";

        SmsVariableRequest smsVariableRequest = new SmsVariableRequest(this.smsUtil.getAccount(), this.smsUtil.getPassword(),
                this.smsUtil.getSmsIdentifyCode(), params, report);

        String requestJson = gson.toJson(smsVariableRequest);

        String response = SmsSender253.sendSmsByPost(this.smsUtil.getUrl(), requestJson);

        SmsVariableResponse resp = gson.fromJson(response, SmsVariableResponse.class);
        boolean success = false;
        //请求成功
        if ("0".equals(resp.getCode())) {
            success = true;
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
     *
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
     *
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

    /**
     * 通用短信通知(单个人)
     *
     * @param msg
     * @param shopName
     * @param orderNo
     * @param phone
     */
    @Override
    public void customOrderMsgSenderSimple(String msg, String shopName, String orderNo, String phone) {
        String params = phone + "," + orderNo + "," + shopName;
        String report = "true";
        customMsgSender(msg, params, report);
    }

    /**
     * 通用短信通知(单个人, 只有订单号)
     *
     * @param msg
     * @param orderNo
     * @param phone
     */
    @Override
    public void customOrderMsgSenderSimpleNoShopName(String msg, String orderNo, String phone) {
        String params = phone + "," + orderNo;
        String report = "true";
        customMsgSender(msg, params, report);
    }

    /**
     * 通用短信通知(批量)
     *
     * @param msg
     * @param shopName
     * @param orderNo
     * @param phoneList
     */
    @Override
    public void customOrderMsgSenderPatch(String msg, String shopName, String orderNo, List<String> phoneList) {
        String params = assemblePhoneList(orderNo, shopName, phoneList);
        String report = "true";
        customMsgSender(msg, params, report);
    }

    /**
     * 通用短信通知(批量, 只有订单号)
     *
     * @param msg
     * @param orderNo
     * @param phoneList
     */
    @Override
    public void customOrderMsgSenderPatchNoShopName(String msg, String orderNo, List<String> phoneList) {
        String params = assemblePhoneList(orderNo, "", phoneList);
        String report = "true";
        customMsgSender(msg, params, report);
    }

    /**
     * 封装联系电话 list->params
     *
     * @param orderNo
     * @param shopName
     * @param phoneList
     * @return
     */
    private String assemblePhoneList(String orderNo, String shopName, List<String> phoneList) {
        String params = "";
        for (int i = 0; i < phoneList.size(); i++) {
            if (StringUtils.isNotBlank(shopName)) {
                params = params + phoneList.get(i) + "," + orderNo + "," + shopName;
            } else {
                params = params + phoneList.get(i) + "," + orderNo;
            }
            if (i < phoneList.size() - 1) {
                params = params + ";";
            }
        }
        return params;
    }

    /**
     * 标准发送短信模式
     *
     * @param msg
     * @param params
     * @param report
     */
    private void customMsgSender(String msg, String params, String report) {
        SmsVariableRequest smsVariableRequest = new SmsVariableRequest(this.smsUtil.getAccount(), this.smsUtil.getPassword(),
                msg, params, report);
        String requestJson = gson.toJson(smsVariableRequest);
        String response = SmsSender253.sendSmsByPost(this.smsUtil.getUrl(), requestJson);
        gson.fromJson(response, SmsVariableResponse.class);
    }

    /**
     * 用户充值完成通知短信
     * @param msg
     * @param chargeNo
     * @param chargeMoney
     * @param balanceMoney
     */
    @Override
    public void UserTopUpUseMsgSender(String msg, String chargeNo, BigDecimal chargeMoney, BigDecimal balanceMoney) {
        String params = chargeNo + "," + chargeMoney + "," + balanceMoney;
        String report = "true";
        customMsgSender(msg, params, report);
    }

    /**
     * 用户充值完成管理员通知短信
     * @param msg
     * @param chargeNo
     * @param userId
     * @param username
     * @param chargeMoney
     * @param balanceMoney
     */
    @Override
    public void UserTopUpAdminMsgSender(String msg, String chargeNo, Integer userId, String username, BigDecimal chargeMoney, BigDecimal balanceMoney) {
        String params = chargeNo + "," + userId + "," + username + "," + chargeMoney + "," + balanceMoney;
        String report = "true";
        customMsgSender(msg, params, report);
    }

    /**
     * 获取配送员联系方式列表
     *
     * @param orderId
     * @return
     */
    @Override
    public List<String> getExpressPhoneListByOrderId(Integer orderId) {
        List<String> resp = new ArrayList<>();
        return Optional.ofNullable(orderId)
                .flatMap(id -> orderInfoRepository.findById(id))
                .map(o -> o.getAreaCode())
                .map(code -> expressRepository.findByAreaCodeAndStatus(code, 1))
                .map(list -> {
                    list.forEach(e -> Optional.ofNullable(e).map(e1 -> e1.getPhone()).map(phone -> resp.add(phone)));
                    return resp;
                })
                .orElse(resp);
    }

    /**
     * 获取配送员联系方式
     *
     * @param orderId
     * @return
     */
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
                }).orElse(null);
    }

    /**
     * 获取管理员联系方式
     *
     * @param orderId
     * @return
     */
    @Override
    public List<String> getAdminPhoneListByOrderId(Integer orderId) {
        List<String> resp = new ArrayList<>();
        return Optional.ofNullable(orderId)
                .flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> o.getAreaCode())
                .flatMap(code -> dicInfoRepository.findByCodeAndAreaCodeAndStatus("admin_phone", code, 1))
                .map(list -> {
                    list.forEach(e -> Optional.ofNullable(e).map(e1 -> e1.getVal()).map(v -> resp.add(v)));
                    return resp;
                })
                .orElse(resp);
    }

    /**
     * 获取商家联系方式
     *
     * @param orderId
     * @return
     */
    @Override
    public String getShopPhoneByOrderId(Integer orderId) {
        return Optional.ofNullable(orderId)
                .flatMap(id -> orderInfoRepository.findById(orderId))
                .map(o -> o.getShopId())
                .flatMap(sId -> shopRepository.findById(sId))
                .map(s -> s.getPhone())
                .orElse(null);
    }

    /**
     * 获取用户联系方式
     *
     * @param orderId
     * @return
     */
    @Override
    public List<String> getUserPhoneListByOrderId(Integer orderId) {
        List<String> resp = new ArrayList<>();
        return Optional.ofNullable(orderId)
                .map(id -> orderAddressRepository.findByOrderId(id))
                .map(list -> {
                    list.forEach(e -> {
                        Optional.ofNullable(e.getPhone()).map(p -> resp.add(p));
                    });
                    return resp;
                }).orElse(resp);
    }
}
