package com.learn.yzh.common.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName:DateUtils
 *
 * @author licho
 * @create 2017-11-29 19:18
 */
public class DateParseUtil {
    private static final Logger log= LoggerFactory.getLogger(DateUtils.class);
    public static final String DATE_PATTERN="yyyy-MM-dd";
    public static final String DATETIME_PATTERN="yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MINUTE_PATTERN="yyyy-MM-dd HH:mm";
    public static Date getToDayBeginTime(){
        SimpleDateFormat dateTimeFormat=new SimpleDateFormat(DATETIME_PATTERN);
        String todayBeginStr=getToDayBeginTimeStr();
        try {
            Date result=dateTimeFormat.parse(todayBeginStr);
            return result;
        } catch (ParseException e) {
            log.debug("日期解析失败",e);
        }
        return null;
    }
    public static Date getToDayEndTime(){
        SimpleDateFormat dateTimeFormat=new SimpleDateFormat(DATETIME_PATTERN);
        String toDayEndTimeStrStr=getToDayEndTimeStr();
        try {
            Date result=dateTimeFormat.parse(toDayEndTimeStrStr);
            return result;
        } catch (ParseException e) {
            log.debug("日期解析失败",e);
        }
        return null;
    }
    public static String getToDayBeginTimeStr(){
        Date today=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_PATTERN);
        String todayBeginStr=dateFormat.format(today)+" 00:00:00";
        return todayBeginStr;
    }

    public static String getToDayEndTimeStr(){
        Date today=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_PATTERN);
        String todayEndStr=dateFormat.format(today)+" 23:59:59";
        return todayEndStr;
    }
    public static Date parseDateStr(String dateStr){
        SimpleDateFormat dateFormat=new SimpleDateFormat(DATE_PATTERN);
        Date parse=null;
        try {
             parse = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.debug("日期解析失败",e);
            throw  new RuntimeException("日期解析失败");
        }
        return parse;
    }

    public static Date parseDateTimeStr(String dateTimeStr){
        SimpleDateFormat dateFormat=new SimpleDateFormat(DATETIME_PATTERN);
        Date parse=null;
        try {
            parse = dateFormat.parse(dateTimeStr);
        } catch (ParseException e) {
            log.debug("日期解析失败",e);
            throw  new RuntimeException("日期解析失败");
        }
        return parse;
    }
    public static String parseDateToStr(Date date,String pattern){
        if(date==null|| StringUtils.isEmpty(pattern)) {
            return null;
        }
        SimpleDateFormat dateFormat=new SimpleDateFormat(pattern);
        String result = dateFormat.format(date);
        return result;
    }

    public static Date parseStrToDateType(String str,String pattern){
        SimpleDateFormat dateFormat=new SimpleDateFormat(pattern);
        Date parse=null;
        try {
            parse = dateFormat.parse(str);
        } catch (ParseException e) {
            log.debug("日期解析失败",e);
            throw  new RuntimeException("日期解析失败");
        }
        return parse;
    }
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }
    public static  String parseToDateTimeStr(Date date){
        return parseDateToStr(date,DATETIME_PATTERN);
    }
    public static  String parseToDateStr(Date date){
        return parseDateToStr(date,DATE_PATTERN);
    }
    /**
     * 判断compare时间是否在[compareTime,end)时间段内。注意是
     * @param compareTime
     * @param begin
     * @param end
     * @return
     */
    public static boolean betweenTimeSegment(Date compareTime,Date begin,Date end){
        if(compareTime==null||begin==null||end==null)
            throw new RuntimeException("日期参数不能为null");
        Calendar compare=Calendar.getInstance();
        Calendar beginTime=Calendar.getInstance();
        Calendar endTime=Calendar.getInstance();
        compare.setTime(compareTime);
        beginTime.setTime(begin);
        endTime.setTime(end);
        boolean flag1=compare.after(beginTime) || compare.equals(beginTime);
        boolean flag2=compare.before(endTime);
        return flag1&&flag2;
    }

    /**
     * 比较两个时间日期字符串代表时间的大小
     * @param d1    d1
     * @param d2    d2
     * @return
     */
    public static int compareDateTimeStr(String d1,String d2){
        Date obj = parseDateTimeStr(d1);
        Date compare = parseDateTimeStr(d2);
        return obj.compareTo(compare);
    }
}
