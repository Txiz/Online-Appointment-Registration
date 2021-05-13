package com.xzx.task.controller;

import com.xzx.common.result.R;
import com.xzx.task.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 对象存储控制器
 * 作者: xzx
 * 创建时间: 2021-05-12-13-38
 **/
@RestController
@RequestMapping("/task/oss")
@Api(tags = "对象存储控制器")
public class OssController {

    @Resource
    private OssService ossService;

    @ApiOperation(value = "上传图片文件")
    @PostMapping("/uploadPicture")
    public R uploadPicture(MultipartFile file) {
        return ossService.uploadPicture(file);
    }
}
