package com.xzx.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 排版规则视图
 * 作者: xzx
 * 创建时间: 2021-05-01-22-35
 **/
@Data
@ApiModel(description = "排版规则视图")
public class ScheduleRuleVo {

    @ApiModelProperty(value = "可预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;

    @ApiModelProperty(value = "可预约日期")
    @JsonFormat(pattern = "MM月dd日")
    private Date workDateMD;

    @ApiModelProperty(value = "周几")
    private String dayOfWeek;

    @ApiModelProperty(value = "就诊医生人数")
    private Integer doctorCount;

    @ApiModelProperty(value = "科室可预约数")
    private Integer reservedNumber;

    @ApiModelProperty(value = "科室剩余预约数")
    private Integer availableNumber;

    @ApiModelProperty(value = "状态 0：正常 1：即将放号 -1：当天已停止挂号")
    private Integer status;
}
