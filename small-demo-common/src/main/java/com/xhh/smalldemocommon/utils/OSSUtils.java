package com.xhh.smalldemocommon.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xhh.smalldemocommon.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 阿里云上传
 */
@Slf4j
public class OSSUtils {

    public static void main(String[] args) throws IOException {
        Map<String,List<User>> map = new HashMap<>();
        List<User> user1 = new ArrayList<>();
        user1.add(new User("xhh"));
        map.put("1", user1);

        List<User> user2 = new ArrayList<>();
        user2.add(new User("xhh2"));
        map.put("2", user2);

        ByteArrayInputStream byteArrayInputStream = uploadZip(map, null);
        byte[] bytes = new byte[1000000];
        byteArrayInputStream.read(bytes);
        File file = new File("d:/nihao.zip");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        
        fileOutputStream.close();
        byteArrayInputStream.close();
    }

    //上传excel打包文件
    public static ByteArrayInputStream uploadZip(Map<String, List<User>> map, String fileName) {
        String url = "";
        ZipOutputStream zipOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(61858764); // 设置大小为60M
            zipOutputStream = new ZipOutputStream(byteArrayOutputStream); // 创建一个压缩流，初始化缓冲区
            for (Map.Entry<String, List<User>> entry : map.entrySet()) {
                XSSFWorkbook workbook = new XSSFWorkbook();
                // 文件名
                ZipEntry zipEntry = new ZipEntry("fileName.xlsx");
                List<User> users = entry.getValue();
                users.forEach((User user) -> {
                    // 
                    String sheetName = "sheetName";
                    Map<Integer, Object> dataMap = new HashMap<>();
                    dataMap.put(1, user.getName());
                    dataMap.put(2, user.getAge());
                    Map<Integer, String> titleMap = new HashMap<>();
                    titleMap.put(1, "name");
                    titleMap.put(2, "age");
                    XSSFSheet sheet = ExcelUtil.buildTitle(workbook, titleMap, "hello", null);
                    ExcelUtil.buildSheetRow(sheet, 1, dataMap, null, null);
                });
                zipOutputStream.putNextEntry(zipEntry);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(61858764);
                workbook.write(outputStream);
                outputStream.writeTo(zipOutputStream);
                outputStream.flush();
                outputStream.close();
                zipOutputStream.flush();
                zipOutputStream.closeEntry();
                zipOutputStream.close();
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();

                //上传阿里云之前必须先关闭流
                inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                //url = this.upload(inputStream, fileName);
            }
        } catch (Exception e) {
            //
            log.error("失败");
        } finally {
            if (null != zipOutputStream) {
                try {
                    zipOutputStream.flush();
                    zipOutputStream.close();
                } catch (Exception e) {
                    log.error("关闭异常");
                }
                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } catch (Exception e) {
                    log.error("关闭异常");
                }

                try {
                    if (null != inputStream) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    log.error("关闭异常");
                }
            }
        }
        // return url;
        return inputStream;
    }

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
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (Exception e) {
                log.error("fileOutputStream关闭异常", e);
            }
        }
        return url;
    }

    /**
     * 上传单个excel文件
     *
     * @param fileName name
     * @param workbook 生成excel工作溥
     * @return url
     */
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

    /**
     * 公共上传方法
     * 流式上传
     *
     * @param inputStream is
     * @param fileName    file
     * @return url
     */
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
