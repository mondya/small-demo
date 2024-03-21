package com.xhh.smalldemocommon.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadUtil {

    static void download(String downloadUrl) {
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        String path = "D:\\下载\\";

        String file = path + fileName;


        try {
            URL url = new URL(downloadUrl);
            URLConnection urlConnection = url.openConnection();


            try (InputStream inputStream = urlConnection.getInputStream();
                 FileOutputStream fops = new FileOutputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(inputStream);
                 BufferedOutputStream bos = new BufferedOutputStream(fops)) {
                int len = -1;
                System.out.println("下载中");
                while ((len = bis.read()) != -1) {
                    bos.write(len);
                }
                System.out.println("下载完毕");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        download("https://wetype.wxqcloud.qq.com/app/win/WeTypeSetup_1.0.5.376_3.exe");
    }
}
