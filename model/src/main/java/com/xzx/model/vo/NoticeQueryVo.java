package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公告条件视图
 * 作者: xzx
 * 创建时间: 2021-05-23-15-26
 **/
@Data
@ApiModel(value = "公告条件视图", description = "")
public class NoticeQueryVo {

    @ApiModelProperty(value = "公告类型（0：平台公告 1：医院公告）")
    private Integer noticeType;

    @ApiModelProperty(value = "公告标题")
    private String noticeTitle;

    @ApiModelProperty(value = "状态（0：锁定 1：正常）")
    private Integer isEnable;

    @ApiModelProperty(value = "创建时间")
    private String beginTime;

    @ApiModelProperty(value = "创建时间")
    private String endTime;
}
