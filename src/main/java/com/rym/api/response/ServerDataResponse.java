package com.rym.api.response;

import com.rym.module.biz.error.FormatableErrorFactory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class ServerDataResponse extends ServerResponse {
    @ApiModelProperty(notes = "响应数据体", dataType = "Object")
    Object data;

    public ServerDataResponse(Object data) {
        super(FormatableErrorFactory.SUCCESS, FormatableErrorFactory.SUCCESS_MSG);
        this.data = data;
    }

//    public ServerDataResponse(int code, String msg, Object data) {
//        super(code, msg);
//        this.data = data;
//    }
}
