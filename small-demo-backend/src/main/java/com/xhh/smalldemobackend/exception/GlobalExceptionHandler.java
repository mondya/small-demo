package com.xhh.smalldemobackend.exception;

import com.xhh.smalldemobackend.module.vo.common.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SmallDemoException.class)
    public ResultVO handlerSmallDemoException(SmallDemoException smallDemoException) {
        log.error("error:",smallDemoException);
        return error(1000, smallDemoException.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResultVO handlerAccessDeniedException(AccessDeniedException accessDeniedException) {
        log.error("error:", accessDeniedException);
        return error(403, "无权限");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResultVO handlerAccessDeniedEntryException(AuthenticationException authenticationException) {
        log.error("error:", authenticationException);
        return error(403, "未登录，请登录");
    }

    @ExceptionHandler(Exception.class)
    public ResultVO handlerException(Exception e) {
        log.error("error :", e);
        return error(9001, "系统繁忙");
    }
    
    public ResultVO error(int code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(0);
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
