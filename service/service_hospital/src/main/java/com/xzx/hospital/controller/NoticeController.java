package com.xzx.hospital.controller;


import com.xzx.common.annotation.LogAnnotation;
import com.xzx.common.result.R;
import com.xzx.hospital.service.NoticeService;
import com.xzx.model.entity.Notice;
import com.xzx.model.vo.NoticeQueryVo;
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
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/hospital/notice")
@Api(tags = "公告控制器")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @ApiOperation(value = "保存公告")
    @PostMapping("/save")
    @LogAnnotation(description = "保存公告", type = OPERATE_LOG)
    public R saveNotice(@RequestBody Notice notice) {
        return noticeService.saveNotice(notice);
    }

    @ApiOperation(value = "更新公告")
    @PostMapping("/update")
    @LogAnnotation(description = "更新公告", type = OPERATE_LOG)
    public R updateNotice(@RequestBody Notice notice) {
        return noticeService.updateNotice(notice);
    }


    @ApiOperation(value = "分页查询公告")
    @PostMapping("/page/{current}/{size}")
    public R pageNotice(@PathVariable Integer current, @PathVariable Integer size, @RequestBody NoticeQueryVo noticeQueryVo) {
        return noticeService.pageNotice(current, size, noticeQueryVo);
    }

    @ApiOperation(value = "根据id查询公告")
    @GetMapping("/{noticeId}")
    public R getNotice(@PathVariable Integer noticeId) {
        return noticeService.getNotice(noticeId);
    }

    @ApiOperation(value = "根据id删除公告")
    @DeleteMapping("/{noticeId}")
    @LogAnnotation(description = "根据id删除公告", type = OPERATE_LOG)
    public R removeNotice(@PathVariable Integer noticeId) {
        return noticeService.removeNotice(noticeId);
    }

    @ApiOperation(value = "启用公告")
    @PutMapping("/enable/{noticeId}/{isEnable}")
    @LogAnnotation(description = "启用公告", type = OPERATE_LOG)
    public R enableNotice(@PathVariable Integer noticeId, @PathVariable Integer isEnable) {
        return noticeService.enableNotice(noticeId, isEnable);
    }

    @ApiOperation(value = "查询公告列表")
    @GetMapping("/list")
    public R listNotice() {
        return noticeService.listNotice();
    }
}

