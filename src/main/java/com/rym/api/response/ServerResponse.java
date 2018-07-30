package com.rym.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rym.module.biz.error.ValidationException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 多彩校园服务器后台响应
 * @author: zqy
 * @date: 2018/4/11 15:43
 * @since: 1.0-SNAPSHOT
 * @note: none
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerResponse implements Serializable {
    @ApiModelProperty(notes = "响应码，非1000均表示出错", dataType = "integer")
    int code;

    @ApiModelProperty(notes = "具体的错误信息", dataType = "string")
    String msg;

    public ServerResponse(ValidationException exception) {
        this.code = exception.getErrorCode();
        this.msg = exception.getMessage();
    }

    @JsonIgnore
    public boolean isSuccess() {
        return 1000 == code;
    }
}
