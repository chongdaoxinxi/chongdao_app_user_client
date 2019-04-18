package com.chongdao.client.controller.protal;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.User;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.response.UserResponse;
import com.chongdao.client.service.UserService;
import com.chongdao.client.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    //注册或登录
   /* @GetMapping("/login")
    @Transactional
    public UserResponse login(User user){
        String username = user.getName();
        String password = user.getPassword();
        UserResponse userResponse = new UserResponse();
        User tUser = userRepository.findByName(username);
        //检验username是否存在
        user.setLastLoginTime(new Date());
        if (tUser != null){
            //检验密码是否正确
            if(!tUser.getPassword().equals(password)) {
                userResponse.setErrorNum(ResultEnum.USER_PWD_ERROR.getCode());
                userResponse.setErrorMsg(ResultEnum.USER_PWD_ERROR.getMessage());
                return userResponse;
            }
            userRepository.updateLastLoginTimeByName(user.getLastLoginTime(),username);
        }else{
            try {
                tUser = userRepository.save(user);
            } catch (Exception e) {
                userResponse.setErrorNum(ResultEnum.ERROR.getCode());
                userResponse.setErrorMsg(ResultEnum.ERROR.getMessage());
                return userResponse;
            }
        }
        userResponse.setErrorNum(ResultEnum.SUCCESS.getCode());
        userResponse.setErrorMsg(ResultEnum.SUCCESS.getMessage());
        userResponse.setUserName(username);
        userResponse.setUserId(tUser.getId());
        userResponse.setToken(TokenUtil.generateToken(username,user.getLastLoginTime()));
        return userResponse;
    }*/

    /**
     * 用户登录接口
     * @param user
     * @return
     */
    @GetMapping("/login")
    public ResultResponse<UserResponse> login(User user){
        ResultResponse<UserResponse> response = userService.login(user.getName(), user.getPassword());
        UserResponse userResponse = new UserResponse();
        if (response.isSuccess()){
            //登录成功后，生成唯一token
            userResponse.setToken(TokenUtil.generateToken(user.getName(),user.getLastLoginTime()));
            userResponse.setUserId(user.getId());
            return ResultResponse.createBySuccess(userResponse);
        }
        return response;
    }

}
