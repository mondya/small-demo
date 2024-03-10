package com.xhh.smalldemobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

// 移除UserDetailsServiceAutoConfiguration自动配置的user，使用自定义的token登入
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableAsync
// 扫描自定义的过滤器，过滤器中需要添加@WebFilter注解，否则不生效
// @ServletComponentScan(basePackages = "com.xhh.smalldemobackend.filter")
public class SmallDemoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallDemoBackendApplication.class, args);
    }

}
