package com.chongdao.client.controller.user;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.service.UserService;
import com.chongdao.client.vo.UserSettingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/5/6
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/setting")
public class UserSettingController {
    @Autowired
    private UserService userService;

    /**
     * 获取用户设置信息
     * @param userId
     * @return
     */
    @GetMapping("/getUserSettingInfo")
    public ResultResponse<UserSettingVO> getUserSettingInfo(Integer userId) {
        return userService.getUserSettingInfo(userId);
    }

    /**
     *  保存用户设置信息
     * @param uso
     * @return
     */
    @GetMapping("/saveUserSetting")
    public ResultResponse saveUserSetting(UserSettingVO uso){
        return userService.saveUserSetting(uso);
    };
}