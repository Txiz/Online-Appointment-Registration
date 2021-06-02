package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单统计视图
 * 作者: Txiz
 * 创建时间: 2021-06-02
 **/
@Data
@ApiModel(description = "订单统计视图")
public class OrderCountVo {

    @ApiModelProperty(value = "安排日期")
    private String reserveDate;

    @ApiModelProperty(value = "预约单数")
    private Integer count;
}
