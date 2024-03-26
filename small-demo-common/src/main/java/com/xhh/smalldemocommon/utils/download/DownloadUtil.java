package com.xhh.smalldemocommon.utils.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DownloadUtil {

    static void download(String downloadUrl) {
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        String path = "D:\\下载\\";

        String file = path + fileName;

        DownloadThread downloadThread = null;

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        try {
            URL url = new URL(downloadUrl);
            URLConnection urlConnection = url.openConnection();


            try (InputStream inputStream = urlConnection.getInputStream();
                 FileOutputStream fops = new FileOutputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(inputStream);
                 BufferedOutputStream bos = new BufferedOutputStream(fops)) {

                long contentLengthLong = urlConnection.getContentLengthLong();
                downloadThread = new DownloadThread(contentLengthLong);
                scheduledExecutorService.scheduleAtFixedRate(downloadThread, 1, 1, TimeUnit.SECONDS);

                byte[] buffer = new byte[102400];
                int len = -1;
                System.out.println("下载中");
                while ((len = bis.read(buffer)) != -1) {
                    downloadThread.downloadSize += len;
                    bos.write(buffer, 0, len);
                }
                System.out.println("下载完毕");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scheduledExecutorService.shutdown();
        }
    }

    public static void main(String[] args) {
        download("https://wetype.wxqcloud.qq.com/app/win/WeTypeSetup_1.0.5.376_3.exe");
    }
}
