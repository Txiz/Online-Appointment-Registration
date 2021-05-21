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
 *
 * </p>
 *
 * @author xzx
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_statistics")
@ApiModel(value = "Statistics对象", description = "")
public class Statistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "统计自增主键")
    @TableId(value = "statistics_id", type = IdType.AUTO)
    private Integer statisticsId;

    @ApiModelProperty(value = "统计日期")
    private String statisticsDate;

    @ApiModelProperty(value = "统计当日登录人数")
    private Integer loginNum;

    @ApiModelProperty(value = "统计当日注册人数")
    private Integer registerNum;

    @ApiModelProperty(value = "统计当日新增异常")
    private Integer exceptionNum;

    @ApiModelProperty(value = "逻辑删除 0：未删除    1:已删除")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
