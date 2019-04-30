package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.User;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.UserStatusEnum;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.UserLoginVO;
import com.chongdao.client.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户端登录注册实现类
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsService smsService;


    /**
     * 用户端登录
     * @return
     */
    @Override
    public ResultResponse<UserLoginVO> login(String phone, String code) {
        if (StringUtils.isBlank(phone)){
            return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USERNAME_OR_CODE_EMPTY.getStatus(), UserStatusEnum.USERNAME_OR_CODE_EMPTY.getMessage());
        }
        User user = userRepository.findByName(phone);
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setLastLoginTime(new Date());
        userLoginVO.setCode(code);
        userLoginVO.setName(phone);
        return assembleUserLogin(userLoginVO,user);

    }

    /**
     * 封装userLogin对象，方便复用
     * 校验手机号是否存在，如果不存在则校验验证码是否正确，通过后则进行注册
     * @param userLoginVO
     * @param user
     * @return
     */
    private ResultResponse<UserLoginVO> assembleUserLogin(UserLoginVO userLoginVO,User user){
        ResultResponse<UserLoginVO> response = checkCodeValid(userLoginVO.getName(),userLoginVO.getCode());
        if (!response.isSuccess()){
                return response;
        }
        User u = new User();
        //用户不存在进行注册
        if (user == null){
            u.setPhone(userLoginVO.getName());
            u.setName(userLoginVO.getName());
            u.setLastLoginTime(new Date());
            userRepository.save(u);
            userLoginVO.setUserId(u.getId());
        }else {
            userLoginVO.setPhone(user.getName());
            userLoginVO.setName(user.getName());
            userLoginVO.setUserId(user.getId());
        }
        //更新用户登录时间
        userRepository.updateLastLoginTimeByName(userLoginVO.getLastLoginTime(), userLoginVO.getName());
        userLoginVO.setToken(TokenUtil.generateToken(userLoginVO.getUserId(),user.getName(),userLoginVO.getLastLoginTime()));
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userLoginVO);
    }
    /**
     * 校验验证码是否正确
     * @param name
     * @return
     */
    private ResultResponse<UserLoginVO> checkCodeValid(String name, String code) {
        //检验验证码是否正确
        if (StringUtils.isNoneBlank(smsService.getSmsCode(name))) {
            if (!smsService.getSmsCode(name).equals(code)) {
                return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USER_CODE_ERROR.getStatus(), UserStatusEnum.USER_CODE_ERROR.getMessage());
            }
        }else {
            return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USER_CODE_ERROR.getStatus(), UserStatusEnum.USER_CODE_ERROR.getMessage());
        }
        return ResultResponse.createBySuccess();
    }


    /**
     * 用户端注册
     * @param userLoginVO
     * @return
     */
/*    @Override
    public ResultResponse<String> register(UserLoginVO userLoginVO) {
        if (StringUtils.isBlank(userLoginVO.getName())){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.USERNAME_OR_CODE_EMPTY.getCode(),
                    ResultEnum.USERNAME_OR_CODE_EMPTY.getMessage());
        }
        ResultResponse<String> response = checkValid(userLoginVO.getName());
        if (!response.isSuccess()){
            return response;
        }
        User user = new User();
        BeanUtils.copyProperties(userLoginVO,user);
        User result = userRepository.save(user);
        if (result == null){
            return ResultResponse.createByError();
        }
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage());
    }*/
}
