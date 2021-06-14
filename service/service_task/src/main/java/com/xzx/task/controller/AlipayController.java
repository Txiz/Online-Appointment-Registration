package com.xzx.task.controller;

import com.xzx.common.result.R;
import com.xzx.task.service.AlipayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 支付宝当面付控制器
 * 作者: xzx
 * 创建时间: 2021-05-13-10-55
 **/
@RestController
@RequestMapping("/task/alipay")
@Api(tags = "支付宝当面付控制器")
public class AlipayController {

    @Resource
    private AlipayService alipayService;

    @ApiOperation("订单支付")
    @GetMapping("/pay/{orderId}")
    public R pay(@PathVariable Integer orderId) {
        return alipayService.pay(orderId);
    }

    @ApiOperation("查询订单支付状态")
    @GetMapping("/queryPayStatus/{orderId}")
    public R queryPayStatus(@PathVariable Integer orderId) {
        return alipayService.queryPayStatus(orderId);
    }

    @ApiOperation("订单取消退款")
    @GetMapping("/refund/{orderId}")
    public R refund(@PathVariable Integer orderId) {
        return alipayService.refund(orderId);
    }
}
