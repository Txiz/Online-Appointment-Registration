package com.xzx.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xzx
 * @since 2021-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user_info")
@ApiModel(value = "UserInfo对象", description = "用户表")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

    @ApiModelProperty(value = "证件类型")
    private String certificatesType;

    @ApiModelProperty(value = "证件编号")
    private String certificatesNumber;

    @ApiModelProperty(value = "证件路径")
    private String certificatesUrl;

    @ApiModelProperty(value = "认证状态（0：未认证 1：认证中 2：认证成功 -1：认证失败）")
    private Integer authStatus;

    @ApiModelProperty(value = "状态（0：锁定 1：正常）")
    private Integer isEnable;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "其它参数")
    @TableField(exist = false)
    private Map<String, Object> param = new HashMap<>();
}
