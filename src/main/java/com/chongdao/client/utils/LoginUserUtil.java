package com.chongdao.client.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginUserUtil {
    private static final String PHONE_REGEX = "^[1](([3|5|8][\\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\\d]{8}$";;
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *i
     * @param target 目标号码
     * @return 如果是手机号码 返回true; 反之,返回false
     */
    public static boolean checkTelephone(String target) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        if(StringUtils.isNotBlank(target)){
            p = Pattern.compile(PHONE_REGEX);
            m = p.matcher(target);
            b = m.matches();
        }
        return b;
        //return PHONE_PATTERN.matcher(target).matches();
    }

    public static void main(String[] args) {
        System.out.println("是正确格式的手机号:"+checkTelephone("17521761654"));
    }

}
