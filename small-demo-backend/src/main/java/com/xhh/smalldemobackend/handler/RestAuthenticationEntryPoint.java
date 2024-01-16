package com.xhh.smalldemobackend.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当未登入或者token失效访问接口时，自定义返回结果
 * 权限空拦截器
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json");
//        ResultVO resultVO = new ResultVO();
//        resultVO.setStatus(0);
//        resultVO.setMessage("登入异常，请重新登入");
//        response.getWriter().println(JSON.toJSONString(resultVO));
//        response.getWriter().flush();
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}
