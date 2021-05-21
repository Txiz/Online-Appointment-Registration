package com.xzx.hospital.controller;


import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.BannerService;
import com.xzx.model.entity.Banner;
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
 * @since 2021-03-29
 */
@RestController
@RequestMapping("/hospital/banner")
@Api(tags = "轮播图控制器")
public class BannerController {

    @Resource
    private BannerService bannerService;

    @ApiOperation(value = "保存轮播图")
    @PostMapping("/save")
    @LogAnnotation(description = "保存轮播图", type = OPERATE_LOG)
    public R saveBanner(@RequestBody Banner banner) {
        return bannerService.saveBanner(banner);
    }

    @ApiOperation(value = "分页查询轮播图")
    @PostMapping("/page/{current}/{size}")
    @LogAnnotation(description = "分页查询轮播图", type = OPERATE_LOG)
    public R pageBanner(@PathVariable Integer current, @PathVariable Integer size) {
        return bannerService.pageBanner(current, size);
    }

    @ApiOperation(value = "根据id删除轮播图")
    @DeleteMapping("/{bannerId}")
    @LogAnnotation(description = "根据id删除轮播图", type = OPERATE_LOG)
    public R removeBanner(@PathVariable Integer bannerId) {
        return bannerService.removeBanner(bannerId);
    }

    @ApiOperation(value = "启用轮播图")
    @PutMapping("/enable/{bannerId}/{isEnable}")
    @LogAnnotation(description = "启用轮播图", type = OPERATE_LOG)
    public R enableBanner(@PathVariable Integer bannerId, @PathVariable Integer isEnable) {
        return bannerService.enableBanner(bannerId, isEnable);
    }

    @ApiOperation(value = "查询轮播图列表")
    @GetMapping("/list")
    @LogAnnotation(description = "查询轮播图列表", type = OPERATE_LOG)
    public R listBanner() {
        return bannerService.listBanner();
    }
}

