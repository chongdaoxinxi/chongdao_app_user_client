package com.chongdao.client.controller.user;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.vo.UserLoginVO;
import com.chongdao.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * @return
     */
    @GetMapping("/login")
    public ResultResponse<UserLoginVO> login(String phone, String code){
        return userService.login(phone, code);
    }






}
