package com.xhh.smalldemocommon.utils;

import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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

    /**
     * 根据时间戳获取字符串str
     * @param stamp
     * @param pattern
     * @return
     */
    public static String getTimeStr(Long stamp, String pattern) {
        String str = "";
        LocalDateTime localDateTime = Instant.ofEpochMilli(stamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
        if (!ObjectUtils.isEmpty(localDateTime)) {
            str = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        }
        
        return str;
    }

    /**
     * 获取一天的开始时间
     * @param stamp
     * @return LocalDateTime
     */
    public static LocalDateTime getDayStartTime(Long stamp){
        LocalDate localDate = Instant.ofEpochMilli(stamp).atOffset(ZoneOffset.ofHours(8)).toLocalDate();
        return localDate.atStartOfDay();
    }

    /**
     * 获取一天的结束时间
     * @param stamp
     * @return LocalDateTime
     */
    public static LocalDateTime getDayEndTime(Long stamp){
        LocalDateTime localDateTime = Instant.ofEpochMilli(stamp).atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();
        return localDateTime.withHour(23).withMinute(59).withSecond(59);
    }


    /**
     * 获取一天结束日期，23:59:59
     * @param stamp 时间戳
     * @return 时间戳
     */
    public static Long getDayEndTimeStamp(Long stamp){
        LocalDateTime localDateTime = Instant.ofEpochMilli(stamp).atOffset(ZoneOffset.ofHours(8)).toLocalDateTime().withHour(23).withMinute(59).withSecond(59);
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 获取一天的开始时间戳
     * @param stamp
     * @return long
     */
    public static Long getDayStartStamp(Long stamp){
        return Instant.ofEpochMilli(stamp).atOffset(ZoneOffset.ofHours(8)).toLocalDate().atStartOfDay().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 获取后一天开始时间戳
     * @param stamp
     * @return
     */
    public static Long getDayEndStamp(Long stamp){
        return Instant.ofEpochMilli(stamp).atOffset(ZoneOffset.ofHours(8)).toLocalDate().atStartOfDay().plusDays(1).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static void main(String[] args) {
//        LocalDate now = LocalDate.now();
//        System.out.println(getTimeStamp(null,now));
//        LocalDateTime nowTime = LocalDateTime.now();
//        System.out.println(getTimeFormat(nowTime,null,"HH:mm"));
//        System.out.println(getDayEndStamp(1653977998000L));
        Date now = new Date();
        long stamp = now.getTime();
        LocalDateTime dayEndTimeStamp = getDayEndTime(stamp);
        LocalDateTime dayStartTime = getDayStartTime(stamp);
        System.out.println(dayEndTimeStamp.until(dayStartTime, ChronoUnit.SECONDS));
    }
}
