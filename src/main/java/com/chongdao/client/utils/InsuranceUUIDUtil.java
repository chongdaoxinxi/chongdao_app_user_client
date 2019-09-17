package com.chongdao.client.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/9/2
 * @Version 1.0
 **/
public class InsuranceUUIDUtil {
    private static final String COMPANY_CODE = "CDXXKJ";

    /**
     * 生成UUID
     *
     * @return
     */
    public static String generateUUID() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        String dateName = df.format(calendar.getTime());
        return COMPANY_CODE + dateName;
    }
}
