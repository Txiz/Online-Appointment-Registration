package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单排班视图
 * 作者: Txiz
 * 创建时间: 2021-06-02
 **/
@Data
@ApiModel(description = "订单排班视图")
public class OrderScheduleVo {

    @ApiModelProperty(value = "医院编号")
    private String hospitalCode;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "科室编号")
    private String departmentCode;

    @ApiModelProperty(value = "科室名称")
    private String departmentName;

    @ApiModelProperty(value = "排班编号（医院自己的排班主键）")
    private String hospitalScheduleId;

    @ApiModelProperty(value = "医生职称")
    private String title;

    @ApiModelProperty(value = "安排日期")
    private Date reserveDate;

    @ApiModelProperty(value = "剩余预约数")
    private Integer availableNumber;

    @ApiModelProperty(value = "安排时间（0：上午 1：下午）")
    private Integer reserveTime;

    @ApiModelProperty(value = "医事服务费")
    private BigDecimal amount;

    @ApiModelProperty(value = "退号时间")
    private Date quitTime;

    @ApiModelProperty(value = "挂号开始时间")
    private Date startTime;

    @ApiModelProperty(value = "挂号结束时间")
    private Date endTime;

    @ApiModelProperty(value = "当天停止挂号时间")
    private Date stopTime;
}
