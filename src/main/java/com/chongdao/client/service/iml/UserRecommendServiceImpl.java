package com.chongdao.client.service.iml;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.enums.ResultEnum;
import com.chongdao.client.enums.RoleEnum;
import com.chongdao.client.service.UserRecommendService;
import com.chongdao.client.utils.LoginUserUtil;
import com.chongdao.client.vo.ResultTokenVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/30
 * @Version 1.0
 **/
@Service
public class UserRecommendServiceImpl implements UserRecommendService {

    /**
     * 本方法返回邀请注册链接地址(附带参数), 前台根据此地址生成微信分享
     * @param token
     * @return
     */
    @Override
    public ResultResponse initRecommendUrl(String token) {
        ResultTokenVo tokenVo = LoginUserUtil.resultTokenVo(token);
        String role = tokenVo.getRole();
        if(StringUtils.isNotBlank(role)) {
            Integer type = 1;
            if(role.equals(RoleEnum.USER.getCode())) {
                //用户
                type = 1;
            } else if(role.equals(RoleEnum.EXPRESS.getCode())) {
                //配送员
                type = 2;
            } else if(role.equals(RoleEnum.SHOP_APP.getCode())) {
                //商家
                type = 3;
            }

            //跳转到指定的邀请注册页面
            String url = "https://www.chongdaopet.com/recommendSign.html" + "?type=" + type + "&recommendId=" + tokenVo.getUserId();
            return ResultResponse.createBySuccess(ResultEnum.SUCCESS.getMessage(), url);
        }
        return ResultResponse.createByErrorCodeMessage(ResultEnum.PARAM_ERROR.getStatus(), ResultEnum.PARAM_ERROR.getMessage());
    }
}
