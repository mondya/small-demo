package com.xhh.smalldemobackend.log;

import com.alibaba.fastjson2.JSONObject;
import com.xhh.smalldemocommon.pojo.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Aspect
@Component
public class LogAop {
    
    static ThreadLocal<LocalDateTime> localDateTimeThreadLocal = new ThreadLocal<>();
    

    @Pointcut("execution(* com.xhh.smalldemobackend.controller..*.*(..))")
    public void log() {
    }


    @Before("log()")
    public void doBefore(JoinPoint joinpoint) {
        localDateTimeThreadLocal.set(LocalDateTime.now());
    }

    private void getHeaderLog(HttpServletRequest request, JoinPoint joinpoint) {
        Long userId = null;
        String userName = null;
        String mobile = null;
        String controllerName = null;
        String methodName = null;

        Optional<SecurityUser> securityUser = Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .filter(p -> p instanceof SecurityUser)
                .map(p -> (SecurityUser) p);

        
        if (securityUser.isPresent()) {
            userId = securityUser.get().getUserId();
            userName = securityUser.get().getUsername();
            mobile = securityUser.get().getMobile();
        }
        StringBuffer url = request.getRequestURL();
//        if ("GET".equals(request.getMethod()) || "PATCH".equals(request.getMethod()) || "EDIT".equals(request.getMethod()) || "PUT".equals(request.getMethod())){
        // getParameterMap()返回的map，vaLue是String[]:例如在请求中/student?studentId=1&studentId=2,此时获取的value应该为"String[] = {"1","2"}，但是一般情况下我们不会去这样传递参数


//        } else if ("POST".equals(request.getMethod())){
//            
//            
//            Object[] args = joinpoint.getArgs();
//            StringBuilder stringBuilder = new StringBuilder();
//            Arrays.stream(args).forEach(stringBuilder::append);
//            body = StringUtils.trimAllWhitespace(stringBuilder.toString());
//            map.put("body", body);
//        }

        controllerName = joinpoint.getSignature().getDeclaringTypeName();
        controllerName = controllerName.substring(controllerName.lastIndexOf("."));
        methodName = joinpoint.getSignature().getName();
/*        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement s : stackTrace) {
            if (s.toString().startsWith("com.xhh.smalldemo.controller")){
                controllerName = s.getClassName().substring(s.getClassName().lastIndexOf('.'), s.getClassName().indexOf("$$"));
                methodName = s.getMethodName();
            }
        }*/
        
        Map<String, Object> map = new HashMap<>();
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        
        for (int i = 0; i < parameterNames.length; i++) {

            if (!(joinpoint.getArgs()[i] instanceof HttpServletRequest) && !(joinpoint.getArgs()[i] instanceof HttpServletResponse)) {
                map.put(parameterNames[i], joinpoint.getArgs()[i]);
            }
        }

        log.info("controller:{}, method:{}, url:{}, params:{}, userParams:{userId:{}, userName:{}, mobile:{}}, , token:{}, 执行时间：{}", controllerName, methodName, url.toString(), JSONObject.toJSONString(map), userId, userName, mobile, request.getHeader("authorization"), Duration.between(localDateTimeThreadLocal.get(), LocalDateTime.now()).toMillis());
        localDateTimeThreadLocal.remove();
    }

    // 使用@After注解

    @After("log()")
    public void doAfterThrowException(JoinPoint joinpoint) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        assert servletRequestAttributes != null;

        HttpServletRequest request = servletRequestAttributes.getRequest();

        getHeaderLog(request, joinpoint);
    }

//    @AfterReturning("log()")
//    public void doAfter(JoinPoint joinpoint) {
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//        assert servletRequestAttributes != null;
//
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//
//        getHeaderLog(request, joinpoint);
//    }
}
