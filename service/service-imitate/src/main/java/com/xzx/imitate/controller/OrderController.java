package com.xzx.imitate.controller;


import com.xzx.common.result.R;
import com.xzx.common.util.HttpRequestUtil;
import com.xzx.common.util.MD5Util;
import com.xzx.imitate.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/imitate/order")
@Api(tags = "订单控制器")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private OrderService orderService;

    @PostMapping("/createOrder")
    @ApiOperation("购买2")
    public R createOrder2(HttpServletRequest request) {
        // 获取请求中的信息参数集合
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestUtil.switchMap(parameterMap);
        // 获取请求中的信息
        String signKey = (String) map.get("signKey");
        Integer hospitalScheduleId = Integer.parseInt((String) map.get("hospitalScheduleId"));
        // 判断签名是否一致
        if (!signKey.equals(MD5Util.encrypt("8sadexcaer"))) {
            return R.error().message("医院签名不一致！");
        }
        // TODO 判断排班是否存在
        // 创建订单
        Map<String, Object> result = new HashMap<>();
        try {
            result = orderService.createOrder(hospitalScheduleId);
            LOGGER.info("创建订单id：[{}]", result.get("id"));
        } catch (Exception e) {
            LOGGER.error("Exception：{}", e.getMessage());
        }
        return R.ok().data(result).message("创建订单成功！");
    }
}

