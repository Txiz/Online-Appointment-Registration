package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 部门科室视图
 * 作者: xzx
 * 创建时间: 2021-05-01-15-10
 **/
@Data
@ApiModel(description = "部门科室视图")
public class DepartmentVo {

    @ApiModelProperty(value = "科室编号")
    private String departmentCode;

    @ApiModelProperty(value = "科室名称")
    private String departmentName;

    @ApiModelProperty(value = "下级节点")
    private List<DepartmentVo> children;
}
