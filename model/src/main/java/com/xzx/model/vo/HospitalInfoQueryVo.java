package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医院信息查询视图
 * 作者: xzx
 * 创建时间: 2021-04-30-23-53
 **/
@Data
@ApiModel(description = "医院信息查询视图")
public class HospitalInfoQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "医院编号")
    private String hospitalCode;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "医院类型")
    private String hospitalType;

    @ApiModelProperty(value = "省code")
    private String provinceCode;

    @ApiModelProperty(value = "市code")
    private String cityCode;

    @ApiModelProperty(value = "区code")
    private String districtCode;

    @ApiModelProperty(value = "状态 0：未上线 1：已上线")
    private Integer status;
}
