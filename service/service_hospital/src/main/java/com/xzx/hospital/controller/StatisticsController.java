package com.xzx.hospital.controller;


import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.StatisticsService;
import com.xzx.model.entity.Statistics;
import com.xzx.model.vo.StatisticsQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.xzx.common.constant.LogConstant.OPERATE_LOG;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-04-12
 */
@RestController
@RequestMapping("/hospital/statistics")
@Api(tags = "统计控制器")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @ApiOperation(value = "保存统计数据")
    @PostMapping("/save")
    @LogAnnotation(description = "保存统计数据", type = OPERATE_LOG)
    public R saveStatistics(@RequestBody Statistics statistics) {
        return statisticsService.saveStatistics(statistics);
    }

    @ApiOperation(value = "分页查询统计数据")
    @PostMapping("/page/{current}/{size}")
    public R pageStatistics(@PathVariable Integer current, @PathVariable Integer size, @RequestBody(required = false) StatisticsQueryVo statisticsQueryVo) {
        return statisticsService.pageStatistics(current, size, statisticsQueryVo);
    }

    @ApiOperation(value = "根据id删除统计数据")
    @DeleteMapping("/{statisticsId}")
    @LogAnnotation(description = "根据id删除统计数据", type = OPERATE_LOG)
    public R removeStatistics(@PathVariable Integer statisticsId) {
        return statisticsService.removeStatistics(statisticsId);
    }

    @ApiOperation(value = "获取统计数据用于制作地图")
    @GetMapping("/getStatisticsDataForMap")
    public R getStatisticsDataForMap() {
        return statisticsService.getStatisticsDataForMap();
    }

    @ApiOperation(value = "从redis中获取当日临时统计数据")
    @GetMapping("/getCurrentStatisticsNum")
    public R getCurrentStatisticsNum() {
        return statisticsService.getCurrentStatisticsNum();
    }
}

