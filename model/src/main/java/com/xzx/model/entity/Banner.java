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
 * @since 2021-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_banner")
@ApiModel(value = "Banner对象", description = "")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "轮播图id")
    @TableId(value = "banner_id", type = IdType.AUTO)
    private Integer bannerId;

    @ApiModelProperty(value = "轮播图路径")
    private String bannerUrl;

    @ApiModelProperty(value = "状态（0：锁定 1：正常）")
    private Integer isEnable;

    @ApiModelProperty(value = "逻辑删除 0：未删除    1:已删除")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
