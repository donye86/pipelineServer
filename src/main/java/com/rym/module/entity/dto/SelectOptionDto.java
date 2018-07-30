package com.rym.module.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 下拉列表选项传值对象
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
@EqualsAndHashCode(doNotUseGetters = true)
public class SelectOptionDto {
    @ApiModelProperty(value = "选项名称", dataType = "string")
    String name;

    @ApiModelProperty(value = "选项属性值", dataType = "string")
    String value;

    @ApiModelProperty(value = "选择状态", dataType = "boolean")
    boolean selected;
}