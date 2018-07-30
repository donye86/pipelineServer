package com.rym.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 多彩校园服务器后台分页数据响应体
 * @author: zqy
 * @date: 2018/4/16 14:29
 * @since: 1.0-SNAPSHOT
 * @note: none
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class PageableData {
    @ApiModelProperty(notes = "当前页位置", dataType = "integer")
    int pageNum;

    @ApiModelProperty(notes = "当前页大小", dataType = "integer")
    int pageSize;

    @ApiModelProperty(notes = "总页数", dataType = "integer")
    int pages;

    @ApiModelProperty(notes = "条目总数", dataType = "long")
    long total;

    @ApiModelProperty(notes = "条目列表", dataType = "List")
    List list;

    @ApiModelProperty(notes = "附带数据", dataType = "List")
    Collection attached;

    public PageableData(int pageSize, Collection attached) {
        this.pageNum = 1;
        this.pageSize = pageSize;
        this.pages = 0;
        this.total = 0;
        this.list = Collections.emptyList();
        this.attached = attached;
    }

    public PageableData(Page page, List items, Collection attached) {
        final int pageIndex = page.getNumber();
        this.pageNum = 0 >= pageIndex ? 1 : pageIndex + 1;
        this.pageSize = page.getSize();
        this.pages = page.getTotalPages();
        this.total = page.getTotalElements();
        this.list = items;
        this.attached = attached;
    }
}
