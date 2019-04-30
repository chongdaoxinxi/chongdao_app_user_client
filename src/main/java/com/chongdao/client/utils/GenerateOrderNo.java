package com.chongdao.client.utils;

import java.util.Random;

public class GenerateOrderNo {

    /**
     * 生成唯一的订单号
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

}
