package com.xzx.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author xzx
 * @since 2021-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_order")
@ApiModel(value = "Order对象", description = "订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "订单交易号")
    private String outTradeNumber;

    @ApiModelProperty(value = "医院编号")
    private String hospitalCode;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "科室编号")
    private String departmentCode;

    @ApiModelProperty(value = "科室名称")
    private String departmentName;

    @ApiModelProperty(value = "医生职称")
    private String title;

    @ApiModelProperty(value = "排班编号（医院自己的排班主键）")
    private String scheduleId;

    @ApiModelProperty(value = "安排日期")
    private Date reserveDate;

    @ApiModelProperty(value = "安排时间（0：上午 1：下午）")
    private Integer reserveTime;

    @ApiModelProperty(value = "就诊人id")
    private Integer patientId;

    @ApiModelProperty(value = "就诊人名称")
    private String patientName;

    @ApiModelProperty(value = "就诊人手机")
    private String patientPhone;

    @ApiModelProperty(value = "预约记录唯一标识（医院预约记录主键）")
    private String hospitalRecordId;

    @ApiModelProperty(value = "预约号序")
    private Integer number;

    @ApiModelProperty(value = "建议取号时间")
    private String fetchTime;

    @ApiModelProperty(value = "取号地点")
    private String fetchAddress;

    @ApiModelProperty(value = "医事服务费")
    private BigDecimal amount;

    @ApiModelProperty(value = "退号时间")
    private Date quitTime;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

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
