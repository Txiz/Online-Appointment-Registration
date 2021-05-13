package com.xzx.hospital.controller;


import com.xzx.common.result.R;
import com.xzx.hospital.service.DataDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
    public R importDataDictionary(MultipartFile file) {
        return dataDictionaryService.importDataDictionary(file);
    }

    @ApiOperation(value = "导出数据字典")
    @PostMapping("/export")
    public R exportDataDictionary(HttpServletResponse response) {
        return dataDictionaryService.exportDataDictionary(response);
    }

    @ApiOperation(value = "根据表id查询子数据字典")
    @GetMapping("/list/{id}")
    public R listDataDictionary(@PathVariable Integer id) {
        return dataDictionaryService.listDataDictionary(id);
    }
}

