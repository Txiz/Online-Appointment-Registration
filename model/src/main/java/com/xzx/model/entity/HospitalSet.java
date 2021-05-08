package com.xzx.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 医院集合表
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_hospital_set")
@ApiModel(value = "HospitalSet对象", description = "医院集合表")
public class HospitalSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "表自增主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "医院编号")
    private String hospitalCode;

    @ApiModelProperty(value = "医院接口路径")
    private String hospitalApiUrl;

    @ApiModelProperty(value = "签名秘钥")
    private String signKey;

    @ApiModelProperty(value = "联系人")
    private String contactsName;

    @ApiModelProperty(value = "联系人手机")
    private String contactsPhone;

    @ApiModelProperty(value = "状态(1:已启用，0:未启用)")
    private Boolean status;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer version;
}
