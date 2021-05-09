package com.xzx.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录视图
 * 作者: xzx
 * 创建时间: 2021-05-08-14-53
 **/
@Data
@ApiModel(description = "用户登录视图")
public class UserLoginVo {

    @ApiModelProperty(value = "支付宝id")
    private String openid;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String code;
}
