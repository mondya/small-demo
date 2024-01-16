package com.xhh.smalldemobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

// 移除UserDetailsServiceAutoConfiguration自动配置的user，使用自定义的token登入
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableAsync
public class SmallDemoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallDemoBackendApplication.class, args);
    }

}
