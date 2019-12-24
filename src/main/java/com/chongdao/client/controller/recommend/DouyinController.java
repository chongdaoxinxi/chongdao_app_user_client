package com.chongdao.client.controller.recommend;

import com.chongdao.client.common.ResultResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/12/24
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/douyin/")
public class DouyinController {

    @GetMapping("monitorUrlIos")
    public ResultResponse monitorUrlIos(String adid, String cid, String idfa, String mac, String os, String timestamp, String callback_url) throws Exception {
        System.out.println("ios_adid>>>" + adid);
        System.out.println("ios_cid>>>" + cid);
        System.out.println("ios_idfa>>>" + idfa);
        System.out.println("ios_mac>>>" + mac);
        System.out.println("ios_os>>>" + os);
        System.out.println("ios_timestamp>>>" + timestamp);
        System.out.println("ios_callback_url>>>" + callback_url);
        return null;
    }

    @GetMapping("monitorUrlAndroid")
    public ResultResponse monitorUrlAndroid(String adid, String cid, String imei, String mac, String oaid, String androidid, String os, String timestamp, String callback_url) throws Exception {
        System.out.println("android_adid>>>" + adid);
        System.out.println("android_cid>>>" + cid);
        System.out.println("android_imei>>>" + imei);
        System.out.println("android_mac>>>" + mac);
        System.out.println("android_oaid>>>" + oaid);
        System.out.println("android_androidid>>>" + androidid);
        System.out.println("android_os>>>" + os);
        System.out.println("android_timestamp>>>" + timestamp);
        System.out.println("android_callback_url>>>" + callback_url);
        return null;
    }
}
