package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户条件视图
 * 作者: xzx
 * 创建时间: 2021-05-12-18-34
 **/
@Data
@ApiModel(description = "用户条件视图")
public class UserQueryVo {

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "是否启用")
    private Integer isEnable;

    @ApiModelProperty(value = "认证状态")
    private Integer authStatus;

    @ApiModelProperty(value = "创建时间")
    private String beginTime;

    @ApiModelProperty(value = "创建时间")
    private String endTime;
}
