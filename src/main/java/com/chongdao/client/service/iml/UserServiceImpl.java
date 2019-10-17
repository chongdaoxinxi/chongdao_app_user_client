package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.entitys.Shop;
import com.chongdao.client.entitys.User;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.UserStatusEnum;
import com.chongdao.client.repository.UserRepository;
import com.chongdao.client.service.SmsService;
import com.chongdao.client.service.UserService;
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


    /**
     * 用户端登录
     * @return
     */
    @Override
    @Transactional
    public ResultResponse<UserLoginVO> login(String phone, String code) {
        if (StringUtils.isBlank(phone)){
            return ResultResponse.createByErrorCodeMessage(UserStatusEnum.USERNAME_OR_CODE_EMPTY.getStatus(), UserStatusEnum.USERNAME_OR_CODE_EMPTY.getMessage());
        }
        User user = userRepository.findByPhone(phone);
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
        userRepository.updateLastLoginTimeByName(userLoginVO.getLastLoginTime(), userLoginVO.getPhone());
        userLoginVO.setToken(TokenUtil.generateToken(userLoginVO.getUserId(),userLoginVO.getName(),userLoginVO.getLastLoginTime(), "USER"));
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
        if(user == null) {
            return ResultResponse.createByErrorMessage("该手机号已经注册过!");
        }

        User u = new User();
        initNewUserCommonFields(u);
        u.setPhone(phone);
        u.setName(phone);//默认名称为手机号码
        u.setRecommendId(recommendId);
        u.setRecommendType(type);
        u.setIsLoginApp(-1);
        return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), userRepository.saveAndFlush(u));
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

    private void initNewUserCommonFields(User u) {
        u.setCreateTime(new Date());
        u.setMoney(new BigDecimal(0));
        u.setPoints(0);
        u.setType(1);
        u.setStatus(1);
    }
}
