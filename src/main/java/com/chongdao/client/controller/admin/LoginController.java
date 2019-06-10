package com.chongdao.client.controller.admin;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.AdminService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description PC端登录
 * @Author onlineS
 * @Date 2019/6/10
 * @Versio.0
 **/
@RestController
@RequestMapping("/api/admin/")
public class LoginController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public ResultResponse login(String username, String password) {
        return adminService.adminLogin(username, password);
    }

    @GetMapping("getAdminInfo")
    public ResultResponse getAdminInfo(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        return adminService.getAdminInfo(tokenVo.getUserId(), tokenVo.getRole());
    }
}
