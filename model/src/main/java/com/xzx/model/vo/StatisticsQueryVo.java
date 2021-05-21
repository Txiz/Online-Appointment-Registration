package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计条件视图
 * 作者: xzx
 * 创建时间: 2021-05-21-13-52
 **/
@Data
@ApiModel(description = "统计条件视图")
public class StatisticsQueryVo {

    @ApiModelProperty(value = "创建时间")
    private String beginTime;

    @ApiModelProperty(value = "创建时间")
    private String endTime;
}
