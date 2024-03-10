package com.xhh.smalldemobackend.filter;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * *@Component注解注册过滤器
 */
@Component
//@WebFilter     WebFilter配置ServletComponentScan扫描注解
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    
//    @Autowired
//    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;
    
//    @Autowired
//    public ExceptionHandlerFilter(HandlerExceptionResolver handlerExceptionResolver) {
//        this.handlerExceptionResolver = handlerExceptionResolver;
//    }
    
//    @Autowired
//    public void setHandlerExceptionResolver(HandlerExceptionResolver handlerExceptionResolver) {
//        this.handlerExceptionResolver = handlerExceptionResolver;
//    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            // 异常处理逻辑
            handlerExceptionResolver.resolveException(request, response, null, ex);
            // 可以根据需要进行其他处理，如记录日志、返回自定义错误页面等
        }
    }
}
