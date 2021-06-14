package com.xzx.hospital.controller;

import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.xzx.common.constant.LogConstant.OPERATE_LOG;

/**
 * 部门科室控制器
 * 作者: xzx
 * 创建时间: 2021-05-01-15-55
 **/
@RestController
@RequestMapping("/hospital/department")
@Api(tags = "部门科室控制器")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @ApiOperation(value = "根据医院编号查询所有的部门科室并组装成列表树")
    @GetMapping("list/{hospitalCode}")
    public R listDepartmentVo(@PathVariable String hospitalCode) {
        return departmentService.listDepartmentVo(hospitalCode);
    }
}
