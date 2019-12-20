package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.entitys.User;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.UserStatusEnum;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.service.UserService;
import com.chongdao.client.service.UserXcxService;
import com.chongdao.client.utils.MD5Util;
import com.chongdao.client.utils.TokenUtil;
import com.chongdao.client.vo.UserLoginVO;
import com.chongdao.client.vo.UserSettingVO;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 用户端登录注册实现类
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserXcxService userXcxService;

    /**
     * 用户端登录
     * @return
     */
    @Override
    @Transactional
    public ResultResponse<UserLoginVO> login(String phone, String code, String password, String type) {
        if (StringUtils.isBlank(phone)){
            return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USERNAME_OR_CODE_EMPTY.getStatus(), UserStatusEnum.USERNAME_OR_CODE_EMPTY.getMessage());
        }
        User user = userRepository.findByPhone(phone);
        if ("2".equals(type) && user == null) {
            return ResultResponse.createByErrorMessage("用户不存在");
        }
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setLastLoginTime(new Date());
        userLoginVO.setCode(code);
        userLoginVO.setName(phone);
        userLoginVO.setPhone(phone);
        if (user != null) {
            userLoginVO.setPassword(user.getPassword());
        }
        return assembleUserLogin(userLoginVO,user,password,type);

    }

    /**
     * 封装userLogin对象，方便复用
     * 校验手机号是否存在，如果不存在则校验验证码是否正确，通过后则进行注册
     * @param userLoginVO
     * @param user
     * @return
     */
    private ResultResponse<UserLoginVO> assembleUserLogin(UserLoginVO userLoginVO,User user,String password,String type){
        ResultResponse<UserLoginVO> response = checkCodeValid(userLoginVO,password,type);
        if (!response.isSuccess()){
                return response;
        }
        User u = new User();
        //用户不存在进行注册
        if (user == null){
            u.setPhone(userLoginVO.getName());
            u.setName(userLoginVO.getName());
            u.setMoney(new BigDecimal(0));
            u.setCreateTime(new Date());
            u.setUpdateTime(new Date());
            u.setLastLoginTime(new Date());
            userRepository.save(u);
            userLoginVO.setUserId(u.getId());
            oldUserXcxReward(u.getPhone());//小程序老用户奖励逻辑
        }else {
            userLoginVO.setPhone(user.getPhone());
            userLoginVO.setName(user.getName());
            userLoginVO.setUserId(user.getId());
            userLoginVO.setNum(1);
        }
        //展示（广告）
        userLoginVO.setIsShow(1);
        //更新用户登录时间
        userRepository.updateLastLoginTimeByName(userLoginVO.getLastLoginTime(), userLoginVO.getPhone());
        userLoginVO.setToken(TokenUtil.generateToken(userLoginVO.getUserId(),userLoginVO.getName(),userLoginVO.getLastLoginTime(), "USER"));
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userLoginVO);
    }

    /**
     * 小程序老用户奖励逻辑
     * @param phone
     */
    private void oldUserXcxReward(String phone) {
        boolean flag = userXcxService.checkIsXcxOldUser(phone);
        System.out.println("flag:>>>>" + flag);
        if(flag) {
            userXcxService.addServiceCpnToXcxUser(phone);
        }
    }

    /**
     * 校验验证码是否正确
     * @param
     * @return
     */
    private ResultResponse<UserLoginVO> checkCodeValid(String name, String code) {
        //检验验证码是否正确
        if ( StringUtils.isNoneBlank(smsService.getSmsCode(name))) {
            if (!smsService.getSmsCode(name).equals(code)) {
                return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USER_CODE_ERROR.getStatus(), UserStatusEnum.USER_CODE_ERROR.getMessage());
            }
        }else {
            return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USER_CODE_ERROR.getStatus(), UserStatusEnum.USER_CODE_ERROR.getMessage());
        }
        return ResultResponse.createBySuccess();
    }


    /**
     * 验证码和密码校验
     * @param userLoginVO
     * @param password
     * @param type
     * @return
     */
    private ResultResponse<UserLoginVO> checkCodeValid(UserLoginVO userLoginVO,String password,String type) {
        //检验验证码是否正确
        if ("1".equals(type) && StringUtils.isNoneBlank(smsService.getSmsCode(userLoginVO.getPhone()))) {
            if (!smsService.getSmsCode(userLoginVO.getPhone()).equals(userLoginVO.getCode())) {
                return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USER_CODE_ERROR.getStatus(), UserStatusEnum.USER_CODE_ERROR.getMessage());
            }
        }else if ("2".equals(type) && !MD5Util.MD5(password).equals(userLoginVO.getPassword())){  //密码校验
            return ResultResponse.createByErrorMessage("密码错误");
        }else {
            return ResultResponse.createBySuccess();
        }
        return ResultResponse.createBySuccess();
    }


    /**
     * 用户端注册
     * @param
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

    @Override
    public ResultResponse<UserSettingVO> getUserSettingInfo(Integer userId) {
        return Optional.ofNullable(userId)
                .map(id -> userRepository.findById(userId).orElse(null))
                .map(u -> {
                    UserSettingVO uso = new UserSettingVO();
                    uso.setName(u.getName());

                    uso.setUserId(u.getId());
                    uso.setPhone(u.getPhone());
                    uso.setIcon(u.getIcon());
                    uso.setType(u.getType());
                    //存在密码，不需要再次设置密码，可以修改
                    if (StringUtils.isNotBlank(u.getPassword())) {
                        uso.setIsPwd(1);
                    }
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), uso);
                }).orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    @Override
    public ResultResponse saveUserSetting(UserSettingVO uso) {
        return Optional.ofNullable(uso).map(usoP -> usoP.getUserId())
                .map(userId -> userRepository.findById(userId).orElse(null))
                .map(u -> {
                    if(StringUtils.isNotBlank(uso.getName())) {
                        u.setName(uso.getName());
                    }
                    if(StringUtils.isNotBlank(uso.getIcon())) {
                        u.setIcon(uso.getIcon());
                    }
                    if(StringUtils.isNotBlank(uso.getPhone())) {
                        u.setPhone(uso.getPhone());
                    }
                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userRepository.saveAndFlush(u));
                }).orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
    }

    @Override
    public ResultResponse<List<Shop>> getFavouriteShopList(Integer userId) {
//        return Optional.ofNullable(userId)
//                .map(id -> shopMapper.getMyFavouriteShopList(id))
//                .map(list -> ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), list))
//                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
        return null;
    }

    @Override
    public ResultResponse<PageInfo> getFavouriteGoodList(Integer userId) {
//        return Optional.ofNullable(userId)
//                .map(id -> goodMapper.getFavouriteGoodList(id))
//                .map(list -> {
//                    PageInfo pi = new PageInfo(list);
//                    pi.setList(goodsListVOList(list));
//                    return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), pi);
//                })
//                .orElse(ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage()));
        return null;
    }

    /**
     * 通过推广链接进行注册
     * @param phone
     * @param type
     * @param recommendId
     * @return
     */
    @Override
    public ResultResponse saveUserRecommendData(String phone, Integer type, Integer recommendId, String code) {
        //校验验证码是否正确
        ResultResponse<UserLoginVO> response = checkCodeValid(phone, code);
        if (!response.isSuccess()){
            return response;
        }
        //校验手机是否已经存在
        User user = userRepository.findByPhone(phone);
        if(user != null) {
            return ResultResponse.createByErrorMessage("该手机号已经注册过!");
        }

        User u = new User();
        initNewUserCommonFields(u);
        u.setPhone(phone);
        u.setName(phone);//默认名称为手机号码
        u.setMoney(new BigDecimal(0));
        u.setRecommendId(recommendId);
        u.setRecommendType(type);
        u.setIsLoginApp(-1);
        User newUser = userRepository.saveAndFlush(u);
        oldUserXcxReward(u.getPhone());//小程序老用户奖励逻辑
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage());
    }

    @Override
    public ResultResponse checkSmsCode(String phone, String code) {
        User user = userRepository.findByPhone(phone);
        if(user != null) {
            return ResultResponse.createByErrorMessage("该手机号已存在!");
        }
        return checkCodeValid(phone, code);
    }

    @Override
    public ResultResponse getUserByPhone(String phone) {
        return ResultResponse.createBySuccess(userRepository.findByPhoneLike("%" + phone + "%"));
    }

    /**
     * 设置密码
     * @param userId
     * @param password
     * @param confirmPassword
     * @return
     */
    @Transactional
    @Override
    public ResultResponse<User> settingPwd(Integer userId, String password, String confirmPassword,String newPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return ResultResponse.createByErrorMessage("两次输入密码不一致,请重新输入");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            //密码为空，代表第一次设置密码
            if (StringUtils.isBlank(user.getPassword())) {
                user.setPassword(MD5Util.MD5(newPassword));
            }else {
                if (StringUtils.isBlank(password)) {
                    return ResultResponse.createByErrorMessage("旧密码不能为空");
                }
                if (!MD5Util.MD5(password).equals(user.getPassword())) {
                    return ResultResponse.createByErrorMessage("旧密码错误,请重新输入");
                }else {
                    user.setPassword(MD5Util.MD5(newPassword));
                }
            }
            userRepository.save(user);
            return ResultResponse.createBySuccess();
        }
        return ResultResponse.createByErrorMessage("用户不存在");
    }

    /**
     * 重置密码
     * @param phone
     * @param code
     * @param password
     * @param confirmPassword
     * @return
     */
    @Override
    public ResultResponse<User> resetPwd(String phone, String code, String password, String confirmPassword) {
        //校验验证码是否正确
        ResultResponse<UserLoginVO> response = checkCodeValid(phone, code);
        if (!response.isSuccess()){
            return ResultResponse.createByErrorMessage("验证码错误");
        }
        if (!password.equals(confirmPassword)) {
            return ResultResponse.createByErrorMessage("两次输入密码不一致,请重新输入");
        }
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            return ResultResponse.createByErrorMessage("该用户不存在");
        }
        user.setPassword(MD5Util.MD5(password));
        userRepository.save(user);
        return ResultResponse.createBySuccess();
    }

    private void initNewUserCommonFields(User u) {
        u.setCreateTime(new Date());
        u.setMoney(new BigDecimal(0));
        u.setPoints(0);
        u.setType(1);
        u.setStatus(1);
    }
}
