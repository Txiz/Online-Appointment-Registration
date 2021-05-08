package com.xzx.model.entity;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 预约规则对象
 * 作者: xzx
 * 创建时间: 2021-04-30-15-43
 **/
@Data
@ApiModel(value = "BookingRule对象", description = "预约规则对象，该对象存在与MongoDB中对应的医院信息表下")
@Document("BookingRule")
public class BookingRule {

    @ApiModelProperty(value = "预约周期")
    private Integer cycle;

    @ApiModelProperty(value = "放号时间")
    private String releaseTime;

    @ApiModelProperty(value = "停挂时间")
    private String stopTime;

    @ApiModelProperty(value = "退号截止天数（如：就诊前一天为-1，当天为0）")
    private Integer quitDay;

    @ApiModelProperty(value = "退号时间")
    private String quitTime;

    @ApiModelProperty(value = "预约规则")
    private List<String> rule;

    public void setRule(String rule) {
        if (!StringUtils.isEmpty(rule)) {
            this.rule = JSONArray.parseArray(rule, String.class);
        }
    }
}
