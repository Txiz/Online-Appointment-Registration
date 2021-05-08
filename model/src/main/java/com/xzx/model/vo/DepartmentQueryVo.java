package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门科室查询视图
 * 作者: xzx
 * 创建时间: 2021-05-01-15-10
 **/
@Data
@ApiModel(description = "部门科室查询视图")
public class DepartmentQueryVo {

    @ApiModelProperty(value = "医院编号")
    private String hospitalCode;

    @ApiModelProperty(value = "科室编号")
    private String departmentCode;

    @ApiModelProperty(value = "科室名称")
    private String departmentName;

    @ApiModelProperty(value = "大科室编号")
    private String bigCode;

    @ApiModelProperty(value = "大科室名称")
    private String bigName;
}
