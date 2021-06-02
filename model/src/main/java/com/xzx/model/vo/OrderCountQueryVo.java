package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 作者: Txiz
 * 创建时间: 2021-06-02
 **/
@Data
@ApiModel(description = "订单统计数据条件视图")
public class OrderCountQueryVo {

    @ApiModelProperty(value = "医院编号")
    private String hospitalCode;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "安排日期")
    private String reserveBeginDate;

    @ApiModelProperty(value = "安排日期")
    private String reserveEndDate;
}
