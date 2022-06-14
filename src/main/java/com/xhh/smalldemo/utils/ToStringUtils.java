package com.xhh.smalldemo.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//字符串处理相关类
@Slf4j
public class ToStringUtils {


    /**
     * String ids cast to Long list
     * 过滤空字符串
     * @param ids String类型的字符串
     * @return list long
     */
    public static List<Long> stringIdsToListLong(String ids) {
        List<Long> idList = new ArrayList<>();
        if (ids != null && !ids.equals("null") && !ids.contains("NaN")) {
            String[] split = ids.split(",");
            List<String> strings = Arrays.asList(split);
            idList = strings.stream().filter((String s) -> !StringUtils.isBlank(s)).map(Long::parseLong).collect(Collectors.toList());
        } else {
            log.error("ids 解析List<Long>失败,ids:{}",ids);
        }
        return idList;
    }
}
