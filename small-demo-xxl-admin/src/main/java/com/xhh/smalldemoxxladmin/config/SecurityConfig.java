//package com.xhh.smalldemoxxladmin.config;
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
///**
// * @EnableGlobalMethodSecurity(prePostEnabled = true) 是Spring Security提供的一个注解，用于启用方法级别的安全性检查。当该注解被应用在配置类上时，它将启用Spring Security的方法级别的安全性检查
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//
////    @Resource
////    ExceptionHandlerFilter exceptionHandlerFilter;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 使用jwt，不需要csrf
//        http.csrf().disable()
//                // 不需要session
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                // 静态资源
//                .antMatchers(HttpMethod.GET,
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js",
//                        "/swagger-resources/**",
//                        "/v2/api-docs/**")
//                .permitAll()
//                // 登入放开
//                .antMatchers("/*")
//                .permitAll()
//                // 跨域请求会先进行一次options请求
//                .antMatchers(HttpMethod.OPTIONS)
//                .permitAll()
//                // 后面所有的接口调用都需要携带token
//                .anyRequest()
//                .authenticated();
//
//        // 禁用缓存
//        http.headers().cacheControl();
//
//
//    }
//}
