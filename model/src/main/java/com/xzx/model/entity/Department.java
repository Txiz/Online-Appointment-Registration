package com.xzx.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 部门科室，MongoDB中的表
 * 作者: xzx
 * 创建时间: 2021-05-01-15-03
 **/
@Data
@ApiModel(value = "Department对象", description = "部门科室，该表存在与MongoDB中")
@Document("Department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "医院编号")
    @Indexed
    private String hospitalCode;

    @ApiModelProperty(value = "科室编号")
    @Indexed(unique = true)
    private String departmentCode;

    @ApiModelProperty(value = "科室名称")
    private String departmentName;

    @ApiModelProperty(value = "科室描述")
    private String introduction;

    @ApiModelProperty(value = "大科室编号")
    private String bigCode;

    @ApiModelProperty(value = "大科室名称")
    private String bigName;

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
    @Transient
    private Map<String, Object> parameter = new HashMap<>();
}
