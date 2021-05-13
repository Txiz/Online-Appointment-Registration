package com.xzx.hospital.controller;


import com.xzx.common.result.R;
import com.xzx.hospital.service.PatientInfoService;
import com.xzx.model.entity.PatientInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 就诊人表 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-05-12
 */
@RestController
@RequestMapping("/hospital/patient-info")
@Api(tags = "就诊人控制器")
public class PatientInfoController {

    @Resource
    private PatientInfoService patientInfoService;

    @ApiOperation(value = "保存就诊人信息")
    @PostMapping("/save")
    public R savePatientInfo(@RequestBody PatientInfo patientInfo, HttpServletRequest request) {
        return patientInfoService.savePatientInfo(patientInfo, request);
    }

    @ApiOperation(value = "更新就诊人信息")
    @PostMapping("/update")
    public R updatePatientInfo(@RequestBody PatientInfo patientInfo) {
        return patientInfoService.updatePatientInfo(patientInfo);
    }

    @ApiOperation(value = "根据表id获取就诊人信息")
    @GetMapping("/{id}")
    public R getPatientInfo(@PathVariable Integer id) {
        return patientInfoService.getPatientInfo(id);
    }

    @ApiOperation(value = "根据表id逻辑删除就诊人信息")
    @DeleteMapping("/{id}")
    public R removePatientInfo(@PathVariable Integer id) {
        return patientInfoService.removePatientInfo(id);
    }

    @ApiOperation(value = "查询所有就诊人信息列表")
    @GetMapping("/list")
    public R listPatientInfo(HttpServletRequest request) {
        return patientInfoService.listPatientInfo(request);
    }
}

