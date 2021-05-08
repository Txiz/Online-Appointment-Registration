package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医院集合查询视图
 * 作者: xzx
 * 创建时间: 2021-04-29-23-20
 **/
@Data
@ApiModel(description = "医院集合查询视图")
public class HospitalSetQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "医院编号")
    private String hospitalCode;
}
