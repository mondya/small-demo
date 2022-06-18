package com.xhh.smalldemo.utils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.util.Map;

//excel 处理
public class ExcelUtil {
    
    // todo
    public static XSSFSheet buildTitle(XSSFWorkbook workbook, Map<String, Object> map, String sheetName){
        XSSFSheet sheet = workbook.createSheet(sheetName);
        if (!CollectionUtils.isEmpty(map)){
            map.forEach((String s, Object o) ->{
                
            });
        }
        return sheet;
    }
}
