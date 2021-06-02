package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者: Txiz
 * 创建时间: 2021-06-02
 **/

@Data
@ApiModel(value = "订单条件视图", description = "")
public class OrderQueryVo {

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "就诊人id")
    private Integer patientId;

    @ApiModelProperty(value = "医院名称")
    private String keyword;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "安排日期")
    private String reserveDate;

    @ApiModelProperty(value = "创建时间")
    private String beginTime;

    @ApiModelProperty(value = "创建时间")
    private String endTime;
}
