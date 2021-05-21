package com.xzx.hospital.controller;


import com.xzx.common.result.R;
import com.xzx.hospital.service.LogService;
import com.xzx.model.entity.Log;
import com.xzx.model.vo.LogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-05-14
 */
@RestController
@RequestMapping("/task/log")
@Api(tags = "日志控制器")
public class LogController {

    @Resource
    private LogService logService;

    @ApiOperation(value = "保存异常日志")
    @PostMapping("/save")
    public R saveLog(@RequestBody Log log) {
        return logService.saveLog(log);
    }

    @ApiOperation(value = "分页查询异常日志")
    @PostMapping("/page/{current}/{size}")
    public R pageLog(@PathVariable Integer current, @PathVariable Integer size, @RequestBody(required = false) LogQueryVo logQueryVo) {
        return logService.pageLog(current, size, logQueryVo);
    }

    @ApiOperation(value = "删除异常日志")
    @DeleteMapping("/{logId}")
    public R removeLog(@PathVariable Integer logId) {
        return logService.removeLog(logId);
    }
}

