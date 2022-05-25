package com.xhh.smalldemo.utils;

import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 阿里云上传
 */
@Slf4j
public class OSSUtils {
    //上传
    void upload(XSSFWorkbook workbook, String fileName) throws IOException {
        File file = new File(fileName);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (Exception e){
            log.error("文件上传失败,e",e);
        } finally {
            if (outputStream != null){
                outputStream.flush();
                outputStream.close();
            }
            workbook.close();
        }
    }
}
