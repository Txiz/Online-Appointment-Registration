package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户认证视图
 * 作者: xzx
 * 创建时间: 2021-05-12-14-09
 **/
@Data
@ApiModel(description = "用户认证视图")
public class UserAuthVo {

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "证件类型")
    private String certificatesType;

    @ApiModelProperty(value = "证件编号")
    private String certificatesNumber;

    @ApiModelProperty(value = "证件路径")
    private String certificatesUrl;
}
