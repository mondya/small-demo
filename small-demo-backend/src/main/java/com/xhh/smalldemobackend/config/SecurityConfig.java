package com.xhh.smalldemobackend.config;

import com.xhh.smalldemobackend.filter.JwtAuthenticationTokenFilter;
import com.xhh.smalldemobackend.handler.RestAuthenticationEntryPoint;
import com.xhh.smalldemobackend.handler.RestfulAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 是Spring Security提供的一个注解，用于启用方法级别的安全性检查。当该注解被应用在配置类上时，它将启用Spring Security的方法级别的安全性检查
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    
//    @Resource
//    ExceptionHandlerFilter exceptionHandlerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用jwt，不需要csrf
        http.csrf().disable()
                // 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                // 静态资源
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**")
                .permitAll()
                // 登入放开
                .antMatchers("/1.0/api/login")
                .permitAll()
                // 跨域请求会先进行一次options请求
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                // 后面所有的接口调用都需要携带token
                .anyRequest()
                .authenticated();
        
        // 禁用缓存
        http.headers().cacheControl();
        
        // 添加JWT filter
        
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 加入filter链中，抛出异常只会打印一次
//        http.addFilterBefore(exceptionHandlerFilter, LogoutFilter.class);
        
        // authentication为空不会抛出异常, exceptionHandlerFilter捕获不到，如果想要捕获异常,jwtAuthenticationTokenFilter把异常抛出，否则需要自定义authenticationEntryPoint
        http.exceptionHandling()
//                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }
    
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
}
