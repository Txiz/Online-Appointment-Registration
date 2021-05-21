package com.xzx.hospital.controller;

import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.HospitalInfoService;
import com.xzx.model.vo.HospitalInfoQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.xzx.common.constant.LogConstant.OPERATE_LOG;

/**
 * 医院信息控制器
 * 作者: xzx
 * 创建时间: 2021-04-30-23-50
 **/
@RestController
@RequestMapping("/hospital/hospital-info")
@Api(tags = "医院信息控制器")
public class HospitalInfoController {

    @Resource
    private HospitalInfoService hospitalInfoService;

    @ApiOperation(value = "分页查询所有医院信息列表")
    @PostMapping("/page/{current}/{size}")
    @LogAnnotation(description = "分页查询所有医院信息列表", type = OPERATE_LOG)
    public R pageHospitalInfo(@PathVariable Integer current, @PathVariable Integer size, HospitalInfoQueryVo hospitalInfoQueryVo) {
        return hospitalInfoService.pageHospitalInfo(current, size, hospitalInfoQueryVo);
    }

    @ApiOperation(value = "更新医院状态，上线医院")
    @GetMapping("/updateStatus/{id}/{status}")
    @LogAnnotation(description = "更新医院状态，上线医院", type = OPERATE_LOG)
    public R updateStatus(@PathVariable String id, @PathVariable Integer status) {
        return hospitalInfoService.updateStatus(id, status);
    }

    @ApiOperation(value = "根据表id获取医院信息")
    @GetMapping("/{id}")
    @LogAnnotation(description = "根据表id获取医院信息", type = OPERATE_LOG)
    public R getHospitalInfo(@PathVariable String id) {
        return hospitalInfoService.getHospitalInfo(id);
    }

    @ApiOperation(value = "根据医院名称获取医院列表")
    @GetMapping("/listByHospitalName/{hospitalName}")
    @LogAnnotation(description = "根据医院名称获取医院列表", type = OPERATE_LOG)
    public R listByHospitalName(@PathVariable String hospitalName) {
        return hospitalInfoService.listByHospitalName(hospitalName);
    }
}
