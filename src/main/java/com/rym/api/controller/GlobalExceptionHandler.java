package com.rym.api.controller;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rym.api.response.ServerResponse;
import com.rym.module.biz.error.ValidationException;

import lombok.extern.log4j.Log4j2;

/**
 * 全局异常处理器
 * @author: zqy
 * @date: 2018/4/11 15:43
 * @since: 1.0-SNAPSHOT
 * @note: none
 */
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ServerResponse HandleValidationException(ValidationException exception) {
        log.error("caught an ValidationException error: ", exception);
        return new ServerResponse(exception);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ServerResponse exceptionHandler(MissingServletRequestParameterException exception) {
        log.error("client request error: ", exception);
        return new ServerResponse(1001, "请求参数错误，缺少参数：" + exception.getParameterName());
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ServerResponse exceptionHandler(HttpMessageNotReadableException exception) {
        log.error("client request error: ", exception);
        return new ServerResponse(1001, "请求参数错误，请查看文档");
    }
    
    @ExceptionHandler(Exception.class)
    public ServerResponse exceptionHandler(Exception exception) {
        log.error("occurs an inner error: ", exception);
        return new ServerResponse(1010, "服务器内部错误，请联系管理员");
    }
}
