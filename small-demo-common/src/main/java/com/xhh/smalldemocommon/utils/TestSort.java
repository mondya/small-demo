package com.xhh.smalldemocommon.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSort {
    public static void main(String[] args) {
        String[] a = {"a", "b", "c", "d", "e", "f"};
        List<String[]> strings = new ArrayList<String[]>();
        combinationSelect(a,0, new String[4], 0, strings);
        strings.forEach((String[] s) -> {
            System.out.println(Arrays.toString(s));
        });
    }
    
    public static void combinationSelect(String[] dataList, int dataIndex, String[] resultList, int resultIndex, List<String[]> strings) {
        int resultLen = resultList.length; // 4
        int resultCount = resultIndex + 1; // 1
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果
            //System.out.println(Arrays.asList(resultList));
            strings.add(resultList);
            return;
        }
        // 递归选择下一个
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) { //dataIndex = 0
            resultList[resultIndex] = dataList[i];
            combinationSelect(dataList, i + 1, resultList, resultIndex + 1, strings);
        }
        
    }
}
