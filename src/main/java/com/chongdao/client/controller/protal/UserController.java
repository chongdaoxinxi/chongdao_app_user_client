package com.chongdao.client.controller.protal;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.User;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.vo.UserLoginVO;
import com.chongdao.client.service.UserService;
import com.chongdao.client.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;



    /**
     * 用户登录接口
     * @param user
     * @return
     */
    @GetMapping("/login")
    public ResultResponse<UserLoginVO> login(UserLoginVO user){
        return userService.login(user);
    }

    @GetMapping("/register")
    public ResultResponse<String>  register(UserLoginVO user){
        return userService.register(user);
    }


}
