package com.xhh.smalldemo.utils;

import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

//时间处理相关类
public class TimeUtil {
    //获取时间戳
    public static long getTimeStamp(LocalDateTime time, LocalDate localDate) {
        long stamp = 0L;
        if (!ObjectUtils.isEmpty(time)) {
            stamp = time.atZone(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        } else if (!ObjectUtils.isEmpty(localDate)) {
            stamp = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        }
        return stamp;
    }

    //获取指定格式的日期时间
    public static String getTimeFormat(LocalDateTime localDateTime, LocalDate localDate, String pattern){
        String format = "";
        if (!ObjectUtils.isEmpty(localDate)){
            format = localDate.format(DateTimeFormatter.ofPattern(pattern));
        } else if (!ObjectUtils.isEmpty(localDateTime)){
            format = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        }
        return format;
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(getTimeStamp(null,now));
        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println(getTimeFormat(nowTime,null,"HH:mm"));
    }
}
