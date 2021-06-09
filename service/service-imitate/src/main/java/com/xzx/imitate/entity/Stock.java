package com.xzx.imitate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xzx
 * @since 2021-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_stock")
@ApiModel(value = "Stock对象", description = "库存表")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "库存")
    private Integer count;

    @ApiModelProperty(value = "已售")
    private Integer sale;
}
