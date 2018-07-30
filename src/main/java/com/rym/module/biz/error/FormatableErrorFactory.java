package com.rym.module.biz.error;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 业务层错误格式化工厂
 * @author: zqy
 * @date: 2018/4/11 15:43
 * @since: 1.0-SNAPSHOT
 * @note: none
 */
public class FormatableErrorFactory {
    String formatString;

    int errorCode;

    public FormatableErrorFactory(int errorCode, String formatString) {
        this.formatString = formatString;
        this.errorCode = errorCode;
    }

    public ValidationException baseException(String ... paras) {
        return new ValidationException(this.errorCode, String.format(formatString, paras));
    }

    public void assertThrowable(boolean throwable, String ... paras) {
        if (throwable) {
            throw baseException(paras);
        }
    }

    public void ifEmptyThrow(Long val, String ... paras) {
        if (val == null || val.longValue() == 0L) {
            throw baseException(paras);
        }
    }

    public void ifEmptyThrow(String str, String ... paras) {
        if (str == null || str.trim().isEmpty()) {
            throw baseException(paras);
        }
    }

    public void ifEmptyThrow(Integer val, String ... paras) {
        if (val == null || val.intValue() == 0) {
            throw baseException(paras);
        }
    }

    public void ifEmptyThrow(Object val, String ... paras) {
        if (val == null) {
            throw baseException(paras);
        }
    }

    public void ifEmptyThrow(Object[] values, String ... paras) {
        if (values == null || 0 == values.length) {
            throw baseException(paras);
        }
    }

    public void ifEmptyThrow(Collection<?> val, String ... paras) {
        if (val == null || val.isEmpty()) {
            throw baseException(paras);
        }
    }

    public void ifEmptyThrow(Map val, String ... paras) {
        if (val == null || val.isEmpty()) {
            throw baseException(paras);
        }
    }

    public void ifNotEmptyThrow(Long val, String ... paras) {
        if (val != null && val.longValue() != 0L) {
            throw baseException(paras);
        }
    }

    public void ifNotEmptyThrow(String str, String ... paras) {
        if (str != null && !str.trim().isEmpty()) {
            throw baseException(paras);
        }
    }

    public void ifNotEmptyThrow(Integer val, String ... paras) {
        if (val != null && val.intValue() != 0) {
            throw baseException(paras);
        }
    }

    public void ifNotEmptyThrow(Object val, String ... paras) {
        if (val != null) {
            throw baseException(paras);
        }
    }

    public void ifNotEmptyThrow(Collection<?> val, String ... paras) {
        if (val != null && !val.isEmpty()) {
            throw baseException(paras);
        }
    }

    public void ifNotEmptyThrow(Map map, String ... paras) {
        if (!CollectionUtils.isEmpty(map)) {
            throw baseException(paras);
        }
    }

    public static final int SUCCESS = 1000;
    public static final String SUCCESS_MSG = "success";
    public static final int INVALID_REQUEST_PARAMETERS = 1001;
    public static final int UNAUTHORIZED_ERROR = 1007;
    public static final int INVALID_REQUEST_SESSION = 1008;
    public static final int REQUEST_SERVER_ERROR = 1009;
    public static final FormatableErrorFactory CAN_NOT_BE_EMPTY = new FormatableErrorFactory(1002, "%s不能为空");
    public static final FormatableErrorFactory NEED_BE_EMPTY = new FormatableErrorFactory(1003, "%s必须为空");
    public static final FormatableErrorFactory NOT_EXIST = new FormatableErrorFactory(1004, "%s不存在");
    public static final FormatableErrorFactory EXISTED = new FormatableErrorFactory(1005, "%s已存在");
    public static final FormatableErrorFactory CUSTOM = new FormatableErrorFactory(1006, "%s");
    public static final FormatableErrorFactory INTERNAL_ERROR = new FormatableErrorFactory(1010, "服务器内部错误，请联系管理员");
}
