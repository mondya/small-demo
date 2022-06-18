package com.xhh.smalldemo.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.mysql.cj.log.Log;
import com.xhh.smalldemo.config.OSSConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;

/**
 * 阿里云上传
 */
@Slf4j
public class OSSUtils {

    // 上传File文件
    String upload(File file) {
        FileInputStream fileInputStream = null;
        String url = "";
        try {
            fileInputStream = new FileInputStream(file);
            this.upload(fileInputStream, file.getName());
        } catch (Exception e) {
            log.error("文件写入失败");
        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (Exception e){
                log.error("fileOutputStream关闭异常",e);
            }
        }
        return url;
    }

    //上传excel文件
    String upload(String fileName, Workbook workbook) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        String url = "";
        try {
            workbook.write(outputStream);
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            url = upload(inputStream, fileName);
        } catch (Exception e) {
            log.error("workbook写入失败");
        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                log.error("outputStream关闭异常");
            }
        }

        return url;
    }

    String upload(InputStream inputStream, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(OSSConfig.END_POINT, OSSConfig.ACCESS_KEY_ID, OSSConfig.ACCESS_KEY_SECRET);
        try {
            ossClient.putObject(OSSConfig.BUCKET_NAME, fileName, inputStream);
        } catch (Exception e) {
            log.error("上传阿里云失败,message:", e);
        } finally {
            ossClient.shutdown();
        }
        return "https://" + OSSConfig.BUCKET_NAME + "." + OSSConfig.END_POINT + "/" + fileName;
    }
}
