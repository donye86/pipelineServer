package com.rym.module.biz.error;

/**
 * 业务层错误异常
 * @author: zqy
 * @date: 2018/4/11 15:43
 * @since: 1.0-SNAPSHOT
 * @note: none
 */
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = -1083172506547838758L;

    int errorCode;

    public ValidationException() {
    }

    public ValidationException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
