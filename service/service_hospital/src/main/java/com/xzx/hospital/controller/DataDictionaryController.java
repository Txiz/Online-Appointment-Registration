package com.xzx.hospital.controller;


import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.DataDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static com.xzx.common.constant.LogConstant.OPERATE_LOG;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
@RestController
@RequestMapping("/hospital/data-dictionary")
@Api(tags = "数据字典控制器")
public class DataDictionaryController {

    @Resource
    private DataDictionaryService dataDictionaryService;


    @ApiOperation(value = "导入数据字典")
    @PostMapping("/import")
    @LogAnnotation(description = "导入数据字典", type = OPERATE_LOG)
    public R importDataDictionary(MultipartFile file) {
        return dataDictionaryService.importDataDictionary(file);
    }

    @ApiOperation(value = "导出数据字典")
    @GetMapping("/export")
    @LogAnnotation(description = "导出数据字典", type = OPERATE_LOG)
    public R exportDataDictionary(HttpServletResponse response) {
        return dataDictionaryService.exportDataDictionary(response);
    }

    @ApiOperation(value = "根据表id查询子数据字典")
    @GetMapping("/list/{id}")
    public R listDataDictionary(@PathVariable Integer id) {
        return dataDictionaryService.listDataDictionary(id);
    }

    @ApiOperation(value = "根据code获取下级节点")
    @GetMapping("/listByCode/{code}")
    public R listByCode(@PathVariable String code) {
        return dataDictionaryService.listByCode(code);
    }
}

