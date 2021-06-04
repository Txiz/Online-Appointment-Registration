package com.xzx.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 就诊人表
 * </p>
 *
 * @author xzx
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_patient_info")
@ApiModel(value = "PatientInfo对象", description = "就诊人表")
public class PatientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "证件类型")
    private String certificatesType;

    @ApiModelProperty(value = "证件编号")
    private String certificatesNumber;

    @ApiModelProperty(value = "性别（0：男 1：女）")
    private Integer sex;

    @JsonFormat(locale = "zh", pattern = "yyyy-MM-dd", timezone = "GMT=8")
    @ApiModelProperty(value = "出生年月")
    private Date birthdate;

    @ApiModelProperty(value = "是否结婚")
    private Integer isMarry;

    @ApiModelProperty(value = "省代码")
    private String provinceCode;

    @ApiModelProperty(value = "市代码")
    private String cityCode;

    @ApiModelProperty(value = "区代码")
    private String districtCode;

    @ApiModelProperty(value = "详情地址")
    private String address;

    @ApiModelProperty(value = "联系人姓名")
    private String contactsName;

    @ApiModelProperty(value = "联系人证件类型")
    private String contactsCertificatesType;

    @ApiModelProperty(value = "联系人证件号")
    private String contactsCertificatesNumber;

    @ApiModelProperty(value = "联系人手机")
    private String contactsPhone;

    @ApiModelProperty(value = "就诊卡号")
    private String cardNumber;

    @ApiModelProperty(value = "是否有医保")
    private Integer isInsure;

    @ApiModelProperty(value = "状态（0：默认 1：已认证）")
    private Integer status;

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
