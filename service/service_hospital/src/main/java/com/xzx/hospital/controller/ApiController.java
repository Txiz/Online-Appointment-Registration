package com.xzx.hospital.controller;

import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.DepartmentService;
import com.xzx.hospital.service.HospitalInfoService;
import com.xzx.hospital.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.xzx.common.constant.LogConstant.OUT_LOG;

/**
 * 对第三方医院提供的接口
 * 作者: xzx
 * 创建时间: 2021-04-30-15-09
 **/
@RestController
@RequestMapping("/api/hospital/")
@Api(tags = "第三方接口控制器")
public class ApiController {

    @Resource
    private HospitalInfoService hospitalInfoService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ScheduleService scheduleService;

    @ApiOperation(value = "上传医院信息")
    @PostMapping("/saveHospitalInfo")
    @LogAnnotation(description = "上传医院信息", type = OUT_LOG)
    public R saveHospitalInfo(HttpServletRequest request) {
        return hospitalInfoService.saveHospitalInfo(request);
    }

    @ApiOperation(value = "根据医院编号查询医院信息")
    @PostMapping("/getHospitalInfoByHospitalCode")
    @LogAnnotation(description = "根据医院编号查询医院信息", type = OUT_LOG)
    public R getHospitalInfoByHospitalCode(HttpServletRequest request) {
        return hospitalInfoService.getHospitalInfoByHospitalCode(request);
    }

    @ApiOperation(value = "上传科室信息")
    @PostMapping("/saveDepartment")
    @LogAnnotation(description = "上传科室信息", type = OUT_LOG)
    public R saveDepartment(HttpServletRequest request) {
        return departmentService.saveDepartment(request);
    }

    @ApiOperation(value = "根据医院编号查询科室信息")
    @PostMapping(("/getDepartmentByHospitalCode"))
    @LogAnnotation(description = "根据医院编号查询科室信息", type = OUT_LOG)
    public R getDepartmentByHospitalCode(HttpServletRequest request) {
        return departmentService.getDepartmentByHospitalCode(request);
    }

    @ApiOperation(value = "根据医院编号和科室编号删除部门科室")
    @PostMapping("/removeDepartment")
    @LogAnnotation(description = "根据医院编号和科室编号删除部门科室", type = OUT_LOG)
    public R removeDepartment(HttpServletRequest request) {
        return departmentService.removeDepartment(request);
    }

    @ApiOperation(value = "上传工作计划")
    @PostMapping("/saveSchedule")
    @LogAnnotation(description = "上传工作计划", type = OUT_LOG)
    public R saveSchedule(HttpServletRequest request) {
        return scheduleService.saveSchedule(request);
    }

    @ApiOperation(value = "根据医院编号和科室编号查询工作计划")
    @PostMapping("/getScheduleByHospitalCodeAndDepartmentCode")
    @LogAnnotation(description = "根据医院编号和科室编号查询工作计划", type = OUT_LOG)
    public R getScheduleByHospitalCodeAndDepartmentCode(HttpServletRequest request) {
        return scheduleService.getScheduleByHospitalCodeAndDepartmentCode(request);
    }

    @ApiOperation(value = "根据医院编号和排班编号删除工作计划")
    @PostMapping("/removeSchedule")
    @LogAnnotation(description = "根据医院编号和排班编号删除工作计划", type = OUT_LOG)
    public R removeSchedule(HttpServletRequest request) {
        return scheduleService.removeSchedule(request);
    }
}
