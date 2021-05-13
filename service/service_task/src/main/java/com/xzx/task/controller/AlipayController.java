package com.xzx.task.controller;

import com.xzx.common.result.R;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝控制器
 * 作者: xzx
 * 创建时间: 2021-05-13-10-55
 **/
@Controller
@RequestMapping("/task/alipay")
@Api(tags = "支付宝控制器")
public class AlipayController {

    @GetMapping("/auth")
    @ResponseBody
    public R auth() {
        return null;
    }
}
