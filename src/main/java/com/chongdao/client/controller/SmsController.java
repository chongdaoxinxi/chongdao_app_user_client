package com.chongdao.client.controller;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信服务
 */

@RestController
@RequestMapping("/sms/")
public class SmsController {



    @Autowired
    private SmsService smsService;


    /**
     * 短信验证码服务
     * @param telephone
     * @return
     */
    @GetMapping(value = "code")
    public ResultResponse smsCode(@RequestParam("telephone") String telephone) {
        if (!LoginUserUtil.checkTelephone(telephone)) {
            return ResultResponse.createByErrorCodeMessage(HttpStatus.BAD_REQUEST.value(),"请输入正确的手机号");
        }
        ResultResponse<String> result = smsService.sendSms(telephone);
        if (result.isSuccess()) {
            return ResultResponse.createBySuccess();
        } else {
            return ResultResponse.createBySuccess(result.getMessage(),HttpStatus.BAD_REQUEST.value());
        }

    }
}
