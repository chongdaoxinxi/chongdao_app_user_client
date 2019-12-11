package com.chongdao.client.utils.mipush;

import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;

import java.util.List;
import java.util.Random;

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
    public static final String USER_IOS_PACKAGENAME = "A6012528353932";//用户端IOS包名
    public static final String SHOP_ANDROID_SECRET = "j04AhW3meDSk+R/JYrV8wA==";//商家端安卓秘钥
    public static final String SHOP_ANDROID_PACKAGENAME = "com.i289503736.pev";//商家端安卓包名
    public static final String SHOP_IOS_SECRET = "wZMwpUoQ6Ttqor1uNGXVXg==";//商家端IOS秘钥
    public static final String SHOP_IOS_PACKAGENAME = "A6019248434955";//商家端IOS秘钥
    public static final String EXPRESS_ANDROID_SECRET = "";//配送端安卓秘钥
    public static final String EXPRESS_ANDROID_PACKAGENAME = "";//配送端安卓包名
    public static final String EXPRESS_IOS_SECRET = "";//配送端IOS秘钥
    public static final String EXPRESS_IOS_PACKAGENAME = "";//配送端IOS包名



    /**
     * 安卓单用户UserAccount推送
     * @param content
     * @param title
     * @param targetAccount
     * @param secret
     * @param packageName
     * @return
     */
    public static String  sendMiPushMessage(String content,String title,String targetAccount,String secret,String packageName){
       Constants.useOfficial();
        Sender sender = new Sender(secret);
//        Message message = new Message.Builder()
//                .title(title)
//                .description(content)
//                .restrictedPackageName(packageName)
//                .notifyType(1)     // 使用默认提示音提示
//                .notifyId(new Random().nextInt(Integer.MAX_VALUE))
//                .build();
        Message message = new Message.Builder()
                .title(title)
                .description(title)
                .payload(content)
                .restrictedPackageName(packageName)
                .passThrough(0)  // 消息使用透传方式
                .notifyType(1)   // 使用默认提示音提示
                .build();
        String code=null;
        try {
            Result result = sender.broadcastAll(message, 3);
            code=result.getErrorCode().getValue()+"";
            ErrorCode ec=result.getErrorCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;

    }

    /**
     * 安卓多用户UserAccount推送
     * @param content
     * @param title
     * @param targetAccount
     * @param secret
     * @param packageName
     * @return
     */
    public static String  sendMiPushMessage(String content, String title, List<String> targetAccount, String secret, String packageName){
        Constants.useOfficial();
        Sender sender = new Sender(secret);
        Message message = new Message.Builder()
                .title(title)
                .description(content)
                .restrictedPackageName(packageName)
                .notifyType(1)     // 使用默认提示音提示
                .notifyId(new Random().nextInt(Integer.MAX_VALUE))
                .build();
        String code=null;
        try {
            Result result = sender.sendToUserAccount(message, targetAccount, 2);

            code=result.getErrorCode().getValue()+"";
            ErrorCode ec=result.getErrorCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;

    }
    /**
     * IOS单用户UserAccount推送
     * @param content
     * @param title
     * @param targetAccount
     * @param clientType
     * @param secret
     * @param packageName
     * @return
     */
    public static String  sendMiPushIOSMessage(String content,String title,String targetAccount,String clientType,String secret,String packageName){
        //Constants.useOfficial();
        //ios 测试环境，不支持安卓
        Constants.useSandbox();
        Sender sender = new Sender(secret);
        Message message = new Message.IOSBuilder()
                .description(content)
                .build();
        String code=null;
        try {
            Result result = sender.sendToUserAccount(message, targetAccount, 2);
            code=result.getErrorCode().getValue()+"";
            ErrorCode ec=result.getErrorCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;

    }

    /**
     * IOS多用户UserAccount推送
     * @param content
     * @param title
     * @param targetAccount
     * @param secret
     * @param packageName
     * @return
     */
    public static String  sendMiPushIOSMessage(String content, String title, List<String> targetAccount, String secret, String packageName){
        Constants.useSandbox();
        Sender sender = new Sender(secret);
        Message message = new Message.IOSBuilder()
                .description(content)
                .build();
        String code=null;
        try {
            Result result = sender.sendToUserAccount(message, targetAccount, 2);
            code=result.getErrorCode().getValue()+"";
            ErrorCode ec=result.getErrorCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;

    }


    /**
     * IOS单用户alias别名推送
     * @param content
     * @param title
     * @param clientType
     * @param secret
     * @param packageName
     * @return
     */
    public static String  sendMiPushIOSMessageByAlias(String content,String title,String targetAccount,String clientType,String secret,String packageName){
        Constants.useOfficial();
        //ios 测试环境，不支持安卓
        //Constants.useSandbox();
        Sender sender = new Sender(secret);
        Message message = new Message.IOSBuilder()
                .description(content)
                .build();
        String code=null;
        try {
            Result result = sender.sendToAlias(message, targetAccount, 3);
            code=result.getErrorCode().getValue()+"";
            ErrorCode ec=result.getErrorCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;

    }
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String content="测试内容3";
        String title="测试标题3";
        //用户id
        String userId="1";
        //密钥(替换自己的)
        String secret="sKPXrKOYbdEWvimXinG3Jw==";
        //包名(替换自己的)
        String packageName="com.h289503736.uzm";
//        String  result2 = MiPushUtil.sendMiPushIOSMessageByAlias(content, title, userId,"1", secret, packageName);
//        System.out.println("result2:"+result2);
        String result3 = MiPushUtil.sendMiPushMessage(content, title, userId, secret, packageName);
        System.out.println("result3:"+result3);
    }
}
