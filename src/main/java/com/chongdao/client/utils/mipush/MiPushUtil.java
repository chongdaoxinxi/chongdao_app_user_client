package com.chongdao.client.utils.mipush;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

import java.util.List;

/**
 * @Description 小米推送工具类
 * @Author onlineS
 * @Date 2019/12/11
 * @Version 1.0
 **/
public class MiPushUtil {
    //    public static final Logger logger = Logger.getLogger(MipushUtils.class);
    public static final String USER_ANDROID_SECRET = "sKPXrKOYbdEWvimXinG3Jw==";//用户端安卓秘钥
    public static final String USER_ANDROID_PACKAGENAME = "com.h289503736.uzm";//用户端安卓包名
    public static final String USER_IOS_SECRET = "NREz2ZAUk7GKGkqnjV3KLw==";//用户端IOS秘钥
    public static final String SHOP_ANDROID_SECRET = "j04AhW3meDSk+R/JYrV8wA==";//商家端安卓秘钥
    public static final String SHOP_ANDROID_PACKAGENAME = "com.i289503736.pev";//商家端安卓包名
    public static final String SHOP_IOS_SECRET = "wZMwpUoQ6Ttqor1uNGXVXg==";//商家端IOS秘钥
    public static final String EXPRESS_ANDROID_SECRET = "";//配送端安卓秘钥
    public static final String EXPRESS_ANDROID_PACKAGENAME = "";//配送端安卓包名
    public static final String EXPRESS_IOS_SECRET = "";//配送端IOS秘钥

    public static final String TITLE = "养宠有道";

    /**
     * 安卓单用户UserAccount推送
     *
     * @param content
     * @param targetAccount
     * @param type          1: 用户端; 2:商家端; 3:配送端
     * @return
     */
    public static String sendMiPushMessage(String content, String targetAccount, Integer type) {
        Constants.useOfficial();
        String secret = getAndroidSecret(type);
        String packageName = getAndroidPackageName(type);
        Sender sender = new Sender(secret);
        Message message = new Message.Builder()
                .title(TITLE)
                .description(content)
                .payload(content)
                .restrictedPackageName(packageName)
                .passThrough(0)  // 消息使用透传方式
                .notifyType(1)   // 使用默认提示音提示
                .build();
        String code = "";
        try {
            Result result = sender.sendToUserAccount(message, targetAccount, 2);
            code = result.getErrorCode().getValue() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 安卓多用户UserAccount推送
     *
     * @param content
     * @param targetAccount
     * @param type          1: 用户端; 2:商家端; 3:配送端
     * @return
     */
    public static String sendMiPushMessage(String content, List<String> targetAccount, Integer type) {
        Constants.useOfficial();
        String secret = getAndroidSecret(type);
        String packageName = getAndroidPackageName(type);
        Sender sender = new Sender(secret);
        Message message = new Message.Builder()
                .title(TITLE)
                .description(content)
                .restrictedPackageName(packageName)
                .passThrough(0)  // 消息使用透传方式
                .notifyType(1)     // 使用默认提示音提示
                .build();
        String code = null;

        try {
            Result result = sender.sendToUserAccount(message, targetAccount, 2);
            code = result.getErrorCode().getValue() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 安卓全设备推送
     *
     * @param content
     * @param type    1: 用户端; 2:商家端; 3:配送端
     * @return
     */
    public static String sendMiPushMessageAll(String content, Integer type) {
        Constants.useOfficial();
        String secret = getAndroidSecret(type);
        String packageName = getAndroidPackageName(type);
        Sender sender = new Sender(secret);
        Message message = new Message.Builder()
                .title(TITLE)
                .description(content)
                .payload(content)
                .restrictedPackageName(packageName)
                .passThrough(0)  // 消息使用透传方式
                .notifyType(1)   // 使用默认提示音提示
                .build();
        String code = "";
        try {
            Result result = sender.broadcastAll(message, 2);
            code = result.getErrorCode().getValue() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    private static String getAndroidSecret(Integer type) {
        String secret = USER_ANDROID_SECRET;
        if(type == 2) {
            secret = SHOP_ANDROID_SECRET;
        } else if(type == 3) {
            secret = EXPRESS_ANDROID_SECRET;
        }
        return secret;
    }

    private static String getAndroidPackageName(Integer type) {
        String packageName = USER_ANDROID_PACKAGENAME;
        if(type == 2) {
            packageName = SHOP_ANDROID_PACKAGENAME;
        } else if(type == 3) {
            packageName = EXPRESS_ANDROID_PACKAGENAME;
        }
        return packageName;
    }

    /**
     * IOS单用户UserAccount推送
     *
     * @param content
     * @param targetAccount
     * @param type          1: 用户端; 2:商家端; 3:配送端
     * @return
     */
    public static String sendMiPushIOSMessage(String content, String targetAccount, Integer type) {
        Constants.useOfficial();
        Sender sender = new Sender(USER_IOS_SECRET);
        Message message = new Message.IOSBuilder()
                .description(content)
                .soundURL("default")    // 消息铃声
                .badge(1)               // 数字角标
                .category("action")     // 快速回复类别
                .extra("key", "value")  // 自定义键值对
                .build();
        String code = null;
        try {
            Result result = sender.sendToUserAccount(message, targetAccount, 2);
            code = result.getErrorCode().getValue() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;

    }

    /**
     * IOS多用户UserAccount推送
     *
     * @param content
     * @param targetAccount
     * @param type          1: 用户端; 2:商家端; 3:配送端
     * @return
     */
    public static String sendMiPushIOSMessage(String content, List<String> targetAccount, Integer type) {
        Constants.useOfficial();
        Sender sender = new Sender(USER_IOS_SECRET);
        Message message = new Message.IOSBuilder()
                .description(content)
                .soundURL("default")    // 消息铃声
                .badge(1)               // 数字角标
                .category("action")     // 快速回复类别
                .extra("key", "value")  // 自定义键值对
                .build();
        String code = null;
        try {
            Result result = sender.sendToUserAccount(message, targetAccount, 2);
            code = result.getErrorCode().getValue() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * IOS全设备推送
     *
     * @param content
     * @param type    1: 用户端; 2:商家端; 3:配送端
     * @return
     */
    public static String sendMiPushIOSMessageAll(String content, Integer type) {
        Constants.useOfficial();
        Sender sender = new Sender(USER_IOS_SECRET);
        Message message = new Message.IOSBuilder()
                .description(content)
                .soundURL("default")    // 消息铃声
                .badge(1)               // 数字角标
                .category("action")     // 快速回复类别
                .extra("key", "value")  // 自定义键值对
                .build();
        String code = null;
        try {
            Result result = sender.broadcastAll(message, 2);
            code = result.getErrorCode().getValue() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        String content = "测试内容3";
        //IOS用户端群体推送
        String result1 = MiPushUtil.sendMiPushIOSMessageAll(content, 1);
        System.out.println("result1:" + result1);
        //Android用户端群体推送
        String result = MiPushUtil.sendMiPushMessageAll(content, 1);
        System.out.println("result:" + result);
        //Android商家端群体推送
        String result2 = MiPushUtil.sendMiPushMessageAll(content, 2);
        System.out.println("result2:" + result2);
    }
}
