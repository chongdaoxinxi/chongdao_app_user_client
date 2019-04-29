package com.chongdao.client.utils;

import com.chongdao.client.common.ResultResponse;
import com.chongdao.client.exception.PetException;
import com.chongdao.client.vo.ResultTokenVo;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginUserUtil {

    private static final String PHONE_REGEX = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(16[0-9])|(18[0,5-9])|(19[0-9]))\\d{8}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    /**
     * 验证手机号码
     *^[1](([3|5|8][\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\d]{8}$
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147、198
     * 联通号码段:130、131、132、136、185、186、145,147,166
     * 电信号码段:133、153、180、189,199
     *i
     * @param target 目标号码
     * @return 如果是手机号码 返回true; 反之,返回false
     */
    public static boolean checkTelephone(String target) {
        Matcher m = null;
        boolean b = false;
        if(StringUtils.isNotBlank(target)){
            m = PHONE_PATTERN.matcher(target);
            b = m.matches();
        }
        return b;
        //return PHONE_PATTERN.matcher(target).matches();
    }


    public static ResultTokenVo resultTokenVo(String token){
        //检验该用户的token
        //将map转化为ResultTokenVo
        ResultTokenVo tokenVo = JsonUtil.map2Obj(TokenUtil.validateToken(token), ResultTokenVo.class);
        //如果返回是200代表用户已登录，否则未登录或者失效
        //登录失败
        if (tokenVo.getUserId() == null){
            throw new PetException(tokenVo.getStatus(),tokenVo.getMessage());
        }
        return tokenVo;
    }


    public static void main(String[] args) {
        System.out.println("是正确格式的手机号:"+checkTelephone("17521761654"));
    }

}
