package com.xzx.hospital.controller;


import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.HospitalSetService;
import com.xzx.model.entity.HospitalSet;
import com.xzx.model.vo.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.xzx.common.constant.LogConstant.OPERATE_LOG;

/**
 * <p>
 * 医院集合表 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
@RestController
@RequestMapping("/hospital/hospital-set")
@Api(tags = "医院集合控制器")
public class HospitalSetController {

    @Resource
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "保存医院集合")
    @PostMapping("/save")
    @LogAnnotation(description = "保存医院集合", type = OPERATE_LOG)
    public R saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        return hospitalSetService.saveHospitalSet(hospitalSet);
    }

    @ApiOperation(value = "更新医院集合")
    @PostMapping("/update")
    @LogAnnotation(description = "更新医院集合", type = OPERATE_LOG)
    public R updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        return hospitalSetService.updateHospitalSet(hospitalSet);
    }

    @ApiOperation(value = "根据表id逻辑删除医院集合")
    @DeleteMapping("/{id}")
    @LogAnnotation(description = "根据表id逻辑删除医院集合", type = OPERATE_LOG)
    public R removeHospitalSet(@PathVariable Integer id) {
        return hospitalSetService.removeHospitalSet(id);
    }


    @ApiOperation(value = "根据表id获取医院集合")
    @GetMapping("/{id}")
    public R getHospitalSet(@PathVariable Integer id) {
        return hospitalSetService.getHospitalSet(id);
    }

    @ApiOperation(value = "查询所有医院集合列表")
    @GetMapping("/list")
    public R listHospitalSet() {
        return hospitalSetService.listHospitalSet();
    }

    @ApiOperation(value = "分页查询所有医院集合列表")
    @PostMapping("/page/{current}/{size}")
    public R pageHospitalSet(@PathVariable Integer current, @PathVariable Integer size, @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        return hospitalSetService.pageHospitalSet(current, size, hospitalSetQueryVo);
    }

    @ApiOperation(value = "跟据表id锁定医院集合")
    @PutMapping("/lock/{id}/{status}")
    @LogAnnotation(description = "跟据表id锁定医院集合", type = OPERATE_LOG)
    public R lockHospitalSet(@PathVariable Integer id, @PathVariable Integer status) {
        return hospitalSetService.lockHospitalSet(id, status);
    }

    @ApiOperation(value = "根据表id获取签名密钥")
    @PutMapping("/getSignKey/{id}")
    public R getSignKeyById(@PathVariable Integer id) {
        return hospitalSetService.getSignKeyById(id);
    }
}

