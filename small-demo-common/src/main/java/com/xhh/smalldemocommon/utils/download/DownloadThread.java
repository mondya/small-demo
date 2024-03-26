package com.xhh.smalldemocommon.utils.download;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class DownloadThread implements Runnable {

    // 已经下载
    volatile long downloadSize;

    // 总大小
    private long totalSize;
    
    // 上次下载大小
    private long preSize;
    
    
    public DownloadThread(Long totalSize) {
        this.totalSize = totalSize;
    }
    
    @Override
    public void run() {
        // 总大小
        BigDecimal bigDecimal = new BigDecimal(totalSize);
        BigDecimal divide = bigDecimal.divide(new BigDecimal("1024")).setScale(2, RoundingMode.HALF_UP);
        
        
        // 计算每秒下载速度 1秒下载多少KB
        double speed = (downloadSize - preSize) / (double)1024;
        
        preSize = downloadSize;

        BigDecimal bigDecimal1 = new BigDecimal(downloadSize);
        bigDecimal1 = bigDecimal1.setScale(2, RoundingMode.HALF_UP);

        // 剩余需要几秒
        double needTime = (totalSize - downloadSize) / 1024d / speed;
        log.info("文件总大小:{}, 已下载大小：{},下载速度：{}, 剩余时间：{}", divide, bigDecimal1, speed, needTime);
        
    }
}
