package com.xzx.model.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 医院信息，MongoDB中的表
 * 作者: xzx
 * 创建时间: 2021-04-30-14-53
 **/
@Data
@ApiModel(value = "HospitalInfo对象", description = "医院信息表，该表存在与MongoDB中")
@Document("HospitalInfo")
public class HospitalInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "医院编号")
    @Indexed(unique = true)
    private String hospitalCode;

    @ApiModelProperty(value = "医院名称")
    @Indexed
    private String hospitalName;

    @ApiModelProperty(value = "医院类型")
    private String hospitalType;

    @ApiModelProperty(value = "省code")
    private String provinceCode;

    @ApiModelProperty(value = "市code")
    private String cityCode;

    @ApiModelProperty(value = "区code")
    private String districtCode;

    @ApiModelProperty(value = "详情地址")
    private String address;

    @ApiModelProperty(value = "医院logo")
    private String logoData;

    @ApiModelProperty(value = "医院简介")
    private String introduction;

    @ApiModelProperty(value = "坐车路线")
    private String route;

    @ApiModelProperty(value = "状态 0：未上线 1：已上线")
    private Integer status;

    @ApiModelProperty(value = "预约规则")
    private BookingRule bookingRule;

    public void setBookingRule(String bookingRule) {
        this.bookingRule = JSONObject.parseObject(bookingRule, BookingRule.class);
    }

    @ApiModelProperty(value = "其它参数")
    @Transient
    private Map<String, Object> parameter = new HashMap<>();
}
