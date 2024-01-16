package com.xhh.smalldemocommon.utils;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
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
     *
     * @param ids String类型的字符串
     * @return list long
     */
    public static List<Long> stringIdsToListLong(String ids) {
        List<Long> idList = new ArrayList<>();
        if (ids != null && !ids.equals("null") && !ids.contains("NaN")) {
            String[] split = ids.split(",");
            idList = Arrays.stream(split).filter((String s) -> !StringUtils.isBlank(s)).map((Long::parseLong)).collect(Collectors.toList());
        } else {
            log.error("ids 解析List<Long>失败,ids:{}", ids);
        }
        return idList;
    }

    /**
     * String ids cast to Long list
     * 过滤空字符串
     *
     * @param ids String类型的字符串
     * @return list long
     */
    public static List<Long> stringIdsToListLongV2(String ids) {
        List<Long> idList = Lists.newArrayList();
        try {
            Iterable<String> split = Splitter.on(",").trimResults().omitEmptyStrings().split(ids);

            split.forEach((String s) -> {
                idList.add(Long.parseLong(s));
            });
        } catch (Exception e) {
            log.error("ids to Long List fail ids:{}, message:{}", ids, e);
        }
        return idList;
    }

    public static String longListToString(List<Long> idList) {
        String s = null;
        if (idList.size() == 0) {
            return "";
        }
        try {
            s = Joiner.on(",").join(idList);
        } catch (Exception e) {
            log.error("解析失败,ids:{},e:", idList, e);
        }
        return s;
    }

    public static void main(String[] args) {
        String s = "1,2";
        List<Long> longs = stringIdsToListLongV2(s);
        System.out.println(longs);
    }
}
