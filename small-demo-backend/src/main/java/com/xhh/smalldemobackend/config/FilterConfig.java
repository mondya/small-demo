package com.xhh.smalldemobackend.config;

import com.xhh.smalldemobackend.filter.ExceptionHandlerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 通过@Bean注解注册过滤器
 */
@Configuration
public class FilterConfig {
    
    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;
    
//    @Bean
//    public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilterFilterRegistrationBean() {
//        FilterRegistrationBean<ExceptionHandlerFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(exceptionHandlerFilter);
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);  // 设置最高优先级
//        registrationBean.addUrlPatterns("/*");  // 拦截所有请求
//        return registrationBean;
//    }
}
