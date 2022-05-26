package com.xhh.smalldemo.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//字符串处理相关类
public class ToStringUtils {


    /**
     * String ids cast to Long list
     * 过滤空字符串
     * @param ids
     * @return
     */
    public static List<Long> stringIdsToListLong(String ids) {
        List<Long> idList = new ArrayList<>();
        if (ids != null && !ids.equals("null")) {
            String[] split = ids.split(",");
            List<String> strings = Arrays.asList(split);
            idList = strings.stream().filter((String s) -> !StringUtils.isBlank(s)).map(Long::parseLong).collect(Collectors.toList());
        }
        return idList;
    }
}
