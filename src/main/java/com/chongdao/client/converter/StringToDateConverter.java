package com.chongdao.client.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @Description TODO
 * @Author onlineS
 * @Date 2019/7/2
 * @Version 1.0
 **/
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        if(StringUtils.isNotBlank(s)) {
            long parseLong = Long.parseLong(s);
            return new Date(parseLong);
        }
        return null;
    }
}
