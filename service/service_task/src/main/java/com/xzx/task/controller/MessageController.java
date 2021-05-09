package com.xzx.task.controller;

import com.xzx.common.result.R;
import com.xzx.task.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 短信验证控制器
 * 作者: xzx
 * 创建时间: 2021-05-08-15-34
 **/
@RestController
@RequestMapping("/task/message")
@Api(tags = "短信验证控制器")
public class MessageController {

    @Resource
    private MessageService messageService;

    @ApiOperation(value = "根据手机号获取短信验证码")
    @PostMapping("/getCode/{phone}")
    public R getCode(@PathVariable String phone) {
        return messageService.getCode(phone);
    }
}
