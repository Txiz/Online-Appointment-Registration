package com.xzx.imitate.controller;


import com.google.common.util.concurrent.RateLimiter;
import com.xzx.common.result.R;
import com.xzx.imitate.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/seckill/order")
@Api(tags = "订单控制器")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;

    RateLimiter rateLimiter = RateLimiter.create(3);

    @GetMapping("/createOrder1/{sid}")
    @ApiOperation("购买1")
    public R createOrder1(@PathVariable int sid) {
        LOGGER.info("等待时间：{}", rateLimiter.acquire());
        LOGGER.info("购买物品编号sid=[{}]", sid);
        try {
            LOGGER.info("创建订单id：[{}]", orderService.createOrder1(sid));
        } catch (Exception e) {
            LOGGER.error("Exception：{}", e.getMessage());
        }
        return R.ok().data("sid", sid);
    }

    @GetMapping("/createOrder2/{sid}")
    @ApiOperation("购买2")
    public R createOrder2(@PathVariable int sid) {
        LOGGER.info("购买物品编号sid=[{}]", sid);
        try {
            LOGGER.info("创建订单id：[{}]", orderService.createOrder2(sid));
        } catch (Exception e) {
            LOGGER.error("Exception：{}", e.getMessage());
        }
        return R.ok().data("sid", sid);
    }
}

