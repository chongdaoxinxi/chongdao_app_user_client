package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.User;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.UserLoginVO;
import com.chongdao.client.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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


    /**
     * 用户端登录
     * @return
     */
    @Override
    public ResultResponse<UserLoginVO> login(UserLoginVO userLoginVO) {
        if (StringUtils.isBlank(userLoginVO.getName())){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.USERNAME_OR_PASSWORD_EMPTY.getCode(),
                    ResultEnum.USERNAME_OR_PASSWORD_EMPTY.getMessage());
        }
        User tUser = userRepository.findByName(userLoginVO.getName());
        //检验username是否存在
        userLoginVO.setLastLoginTime(new Date());
        if (tUser != null){
            //检验密码是否正确
            if(!tUser.getPassword().equals(userLoginVO.getPassword())) {
                return ResultResponse.createByErrorCodeMessage(ResultEnum.USER_PWD_ERROR.getCode(),ResultEnum.USER_PWD_ERROR.getMessage());
            }
            //更新用户登录时间
            userRepository.updateLastLoginTimeByName(userLoginVO.getLastLoginTime(),userLoginVO.getName());
            userLoginVO.setName(tUser.getName());
            userLoginVO.setUserId(tUser.getId());
            userLoginVO.setToken(TokenUtil.generateToken(tUser.getId(),tUser.getName(),tUser.getLastLoginTime()));
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(),userLoginVO);
        }
        return ResultResponse.createByError();
    }

    /**
     * 用户端注册
     * @param userLoginVO
     * @return
     */
    @Override
    public ResultResponse<String> register(UserLoginVO userLoginVO) {
        if (StringUtils.isBlank(userLoginVO.getName())){
            return ResultResponse.createByErrorCodeMessage(ResultEnum.USERNAME_OR_PASSWORD_EMPTY.getCode(),
                    ResultEnum.USERNAME_OR_PASSWORD_EMPTY.getMessage());
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
    }

    /**
     * 注册用户名是否存在
     * @param str
     * @return
     */
    private ResultResponse<String> checkValid(String str){
        if (StringUtils.isNotBlank(str)){
            int result = userRepository.checkUserName(str);
            if (result > 0){
                //说明该用户已存在，不可重复注册
                return ResultResponse.createByErrorMessage("该用户已存在");
            }
        }else{
            return ResultResponse.createByErrorMessage("校验失败");
        }
        return ResultResponse.createBySuccessMessage("校验成功");
    }
}
