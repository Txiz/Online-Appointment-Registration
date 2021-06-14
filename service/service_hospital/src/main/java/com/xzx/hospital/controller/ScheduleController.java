package com.xzx.hospital.controller;

import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.xzx.common.constant.LogConstant.OPERATE_LOG;

/**
 * 工作计划控制器
 * 作者: xzx
 * 创建时间: 2021-05-01-20-21
 **/
@RestController
@RequestMapping("/hospital/schedule")
@Api(tags = "工作计划控制器")
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    @ApiOperation(value = "根据医院编号和科室编号分页查询排班规则")
    @GetMapping("/getScheduleRule/{current}/{size}/{hospitalCode}/{departmentCode}")
    public R getScheduleRule(@PathVariable Integer current, @PathVariable Integer size, @PathVariable String hospitalCode, @PathVariable String departmentCode) {
        return scheduleService.getScheduleRule(current, size, hospitalCode, departmentCode);
    }

    @ApiOperation(value = "根据医院编号、科室编号和工作日期查询工作计划")
    @GetMapping("/getSchedule/{hospitalCode}/{departmentCode}/{workDate}")
    public R getSchedule(@PathVariable String hospitalCode, @PathVariable String departmentCode, @PathVariable String workDate) {
        return scheduleService.getSchedule(hospitalCode, departmentCode, workDate);
    }

    @ApiOperation(value = "根据医院编号和科室编号分页查询可预约排班规则")
    @GetMapping("/getBookingSchedule/{current}/{size}/{hospitalCode}/{departmentCode}")
    public R getBookingSchedule(@PathVariable Integer current, @PathVariable Integer size, @PathVariable String hospitalCode, @PathVariable String departmentCode) {
        return scheduleService.getBookingSchedule(current, size, hospitalCode, departmentCode);
    }
}
