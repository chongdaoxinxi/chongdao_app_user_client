package com.chongdao.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String STR_TO_DATE_FORMAT = "yyyy-MM-dd";

    //字符串转换成日期类型
    public static Date strToDate(String dateTimeStr, String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * 日期转换字符串
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToStr(Date date, String formatStr){
        if (date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }


    /**
     * 字符串转换日期 默认格式
     * @param dateTimeStr
     * @return
     */
    public static Date strToDate(String dateTimeStr){
        if(dateTimeStr.indexOf("-") == -1) {
            StringBuilder sb = new StringBuilder(dateTimeStr);
            sb.insert(4, "-");
            sb.insert(7, "-");
            sb.insert(10, " ");
            sb.insert(13, ":");
            sb.insert(16, ":");
            dateTimeStr = sb.toString();
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * 日期转换字符串默认格式
     * @param date
     * @return
     */
    public static String dateToStr(Date date){
        if (date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    public static String dateToStr2(Date date){
        if (date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STR_TO_DATE_FORMAT);
    }

    public static Time strToTime(String str) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        java.util.Date d = null;
        if(StringUtils.isBlank(str)) {
            return null;
        }
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Time time = new java.sql.Time(d.getTime());
        return time.valueOf(str);
    }

    public static int costTimeDay(String  endTime, String  startTime) {
        long from = 0;
        long to = 0;
        try {
            from = getDateFormat(STANDARD_FORMAT).parse(startTime).getTime();
            to = getDateFormat(STANDARD_FORMAT).parse(endTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
        return Math.abs(days);
    }


    public static long costTime(String  endTime, String  startTime) {
        long from = 0;
        long to = 0;
        try {
            from = getDateFormat(STANDARD_FORMAT).parse(startTime).getTime();
            to = getDateFormat(STANDARD_FORMAT).parse(endTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = (to - from)/1000;
        return time;
    }
    protected static DateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format);
    }
}
