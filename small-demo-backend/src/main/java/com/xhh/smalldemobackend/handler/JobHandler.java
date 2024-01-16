package com.xhh.smalldemobackend.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobHandler {
    
    @XxlJob("demoJobHandler")
    public ReturnT<String> demoJobHandler(String param) {
        log.info("xxl-job, hello world");
        return ReturnT.SUCCESS;
    }
}
