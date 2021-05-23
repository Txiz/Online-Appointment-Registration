package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 日志条件视图
 * 作者: xzx
 * 创建时间: 2021-05-14-15-16
 **/
@Data
@ApiModel(value = "日志条件视图", description = "")
public class LogQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "方法类型 0：登录日志 1：操作日志 2：异常日志")
    private Integer logType;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "请求接口")
    private String uri;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "IP来源")
    private String ipSource;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "浏览器")
    private String browser;
}
