package com.chongdao.client.controller.user;


import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.PetCard;
import com.chongdao.client.entitys.UserAccount;
import com.chongdao.client.service.PetCardService;
import com.chongdao.client.service.UserAccountService;
import com.chongdao.client.vo.UserLoginVO;
import com.chongdao.client.service.UserService;
import com.chongdao.client.vo.UserSettingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PetCardService petCardService;
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 用户登录接口
     * @return
     */
    @GetMapping("/login")
    public ResultResponse<UserLoginVO> login(String phone, String code){
        return userService.login(phone, code);
    }

    /**
     *  获取用户的宠物卡片列表
     * @param userId
     * @param status
     * @return
     */
    @GetMapping("/getPetCardList")
    public ResultResponse<List<PetCard>> getPetCardList(Integer userId, Integer status) {
        return petCardService.getPetCardByUserIdAndStatus(userId, status);
    }

    /**
     * 获取宠物卡片详细信息
     * @param cardId
     * @return
     */
    @GetMapping("/getPetCardInfo")
    public ResultResponse<PetCard> getPetCardInfo(Integer cardId) {
        return petCardService.getPetCardById(cardId);
    }

    /**
     * 保存宠物卡片信息
     * @param petCard
     * @return
     */
    @GetMapping("/savePetCard")
    public ResultResponse savePetCard(PetCard petCard) {
        return petCardService.savePetCard(petCard);
    }

    /**
     *
     * 获取用户账户信息
     * @param userId
     * @return
     */
    @GetMapping("/getUserAccount")
    public ResultResponse<UserAccount> getUserAccount(Integer userId) {
        return userAccountService.getUserAccountByUserId(userId);
    }

    /**
     * 保存用户账户信息
     * @param ua
     * @return
     */
    @GetMapping("/saveUserAccount")
    public ResultResponse saveUserAccount(UserAccount ua) {
        return userAccountService.saveUserAccount(ua);
    }

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
