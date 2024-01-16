package com.xhh.smalldemocommon.utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.util.Map;

//excel 处理
public class ExcelUtil {
    

    /**
     * 创建红色加粗字体 首行锁定表头
     * @param workbook
     * @param map
     * @param sheetName
     */
    static XSSFSheet buildTitle(XSSFWorkbook workbook, Map<Integer, String> map, String sheetName, String tips) {
        XSSFSheet sheet = workbook.createSheet(sheetName);
        int titleIdx = 0;
        if (tips != null) {
            //提示
            XSSFCellStyle blockFontStyle = workbook.createCellStyle();
            XSSFFont blackBoldFont = workbook.createFont();
            blackBoldFont.setColor(IndexedColors.BLACK.index);
            blackBoldFont.setBold(true);

            //设置Excel文本值换行主要代码设置样式 setWrapText(),
            blockFontStyle.setWrapText(true);
            blockFontStyle.setLocked(true);
            blockFontStyle.setFont(blackBoldFont);
            //设置水平对齐的样式为居中对齐;
            blockFontStyle.setAlignment(HorizontalAlignment.LEFT);
            //设置垂直对齐的样式为居中对齐;
            blockFontStyle.setVerticalAlignment(VerticalAlignment.TOP);
            Row row = sheet.createRow(titleIdx);
            createCell(row, 0, new XSSFRichTextString(tips), null, blockFontStyle);
            row.setHeightInPoints(90F);
            // (4个参数，分别为起始行，结束行，起始列，结束列)
            // 行和列都是从0开始计数，且起始结束都会合并
            CellRangeAddress region = new CellRangeAddress(0, 0, 0, map.size() - 1);
            sheet.addMergedRegion(region);
            titleIdx++;
        }
        XSSFCellStyle redFontStyle = workbook.createCellStyle();
        XSSFFont redBoldFont = workbook.createFont();
        redBoldFont.setColor(IndexedColors.RED.index);
        redBoldFont.setBold(true);
        redBoldFont.setFontHeightInPoints((short) 13);

        //设置水平对齐的样式为居中对齐;
        redFontStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        redFontStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        redFontStyle.setLocked(true);
        redFontStyle.setFont(redBoldFont);
        Row row = sheet.createRow(titleIdx);
        map.forEach((Integer k, String v) ->{
            createCell(row, k, v, null, redFontStyle);
            sheet.setColumnWidth(k, 4500);
        });
        row.setHeightInPoints(25F);
        sheet.createFreezePane(0, titleIdx + 1, 0, titleIdx + 1);
        return sheet;
    }


    /**
     * 根据 map 创建行数据
     *
     * @param sheet
     * @param map
     */
    static void buildSheetRow(Sheet sheet, int i, Map<Integer, Object> map, CellType type, CellStyle style) {
        Row row = sheet.getRow(i);
        if (row == null) {
            row = sheet.createRow(i);
        }
        
        for (Map.Entry<Integer, Object> entry : map.entrySet()){
            createCell(row, entry.getKey(), entry.getValue(), null, style);
        }
        row.setHeightInPoints(20F);
    }

    /**
     * type 暂时只支持 FORMULA 处理
     * @param row
     * @param column
     * @param value
     * @param type
     */
    static void createCell(Row row, Integer column, Object value, CellType type, CellStyle style) {
        if (column != null) {
            Cell cell = null;
            if (value == null) {
                cell = row.createCell(column, CellType.BLANK);
            } else if (type == CellType.FORMULA) {
                cell = row.createCell(column, CellType.FORMULA);
                cell.setCellFormula(value == null ? "" : value.toString());
            } else if (type != null) {
                cell = row.createCell(column, type);
                cell.setCellValue(value == null ? "" : value.toString());
            } else {
                cell = row.createCell(column, CellType.STRING);
                cell.setCellValue(value == null ? "" : value.toString());
            }
            if (style != null) {
                cell.setCellStyle(style);
            }
        }
    }
}
