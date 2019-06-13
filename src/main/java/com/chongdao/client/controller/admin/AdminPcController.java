package com.chongdao.client.controller.admin;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 管理员pc端
 * @Author onlineS
 * @Date 2019/6/10
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/admin_pc/")
public class AdminPcController {

    @GetMapping("confirmRefund")
    public ResultResponse confirmRefund(String token, String orderId) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return null;
    }
}
