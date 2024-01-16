package com.xhh.smalldemobackend.filter;

import com.alibaba.fastjson2.JSONObject;
import com.xhh.smalldemocommon.pojo.SecurityUser;
import com.xhh.smalldemocommon.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        
        String jwt = resolveToken(httpServletRequest);
        
        if (StringUtils.isNotBlank(jwt)){
//            String[] jwtParts = jwt.split("\\.",0);
            /**
             * 当 authentication 为 null 时，Spring Security 认为当前用户是一个未认证的匿名用户。
             * 根据安全配置，如果一个请求需要身份验证（即需要用户具有有效的身份验证凭据），但当前用户是匿名用户，Spring Security 会将用户重定向到 403 页面。
             */
            Authentication authentication = getAuthenticationByBase64(jwt, httpServletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        chain.doFilter(request,response);
    }
    
    
    private String resolveToken(HttpServletRequest request){
        String token = request.getHeader("authorization");
        log.info("token----------------> {}", token);
        
        if (StringUtils.isNotBlank(token) && token.startsWith("Bearer ")){
            return token.substring(7, token.length());
        } else {
            return token;
        }
    }
    
    public Authentication getAuthenticationByBase64(String payLoad, HttpServletRequest request){
        
//        String payLoadStr = new String(Base64.getUrlDecoder().decode(payLoad));
        
        String payLoadStr = JwtTokenUtil.getClaimsFromToken(payLoad);

        JSONObject jsonObject = JSONObject.parseObject(payLoadStr);
        
//        Long userId = jsonObject.getJSONObject("iss").getLong("userId");
//        String userName = jsonObject.getJSONObject("iss").getString("userName");
//        String mobile = jsonObject.getJSONObject("iss").getString("mobile");   
        SecurityUser securityUser = new SecurityUser();
        if (ObjectUtils.isNotEmpty(jsonObject)){
            Long userId = jsonObject.getLong("userId");
            String userName = jsonObject.getString("userName");
            String mobile = jsonObject.getString("mobile");

            securityUser.setUserId(userId);
            securityUser.setUserName(userName);
            securityUser.setMobile(mobile);

            return new UsernamePasswordAuthenticationToken(securityUser, "", securityUser.getAuthorities());
        } else {
            return null;
        }
        
    }
}
