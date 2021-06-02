package com.xzx.hospital.controller;


import com.xzx.common.result.R;
import com.xzx.common.util.AuthUtil;
import com.xzx.hospital.service.OrderService;
import com.xzx.model.vo.OrderCountQueryVo;
import com.xzx.model.vo.OrderQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-06-02
 */
@RestController
@RequestMapping("/hospital/order")
@Api(tags = "订单控制器")
public class OrderController {

    @Resource
    private OrderService orderService;

    @ApiOperation(value = "保存订单")
    @GetMapping("/save/{scheduleId}/{patientId}")
    public R saveOrder(@PathVariable String scheduleId, @PathVariable Integer patientId) {
        return orderService.saveOrder(scheduleId, patientId);
    }

    @ApiOperation(value = "分页查询所有订单列表")
    @GetMapping("/page/{current}/{size}")
    public R pageOrder(@PathVariable Integer current, @PathVariable Integer size, OrderQueryVo orderQueryVo, HttpServletRequest request) {
        orderQueryVo.setUsername(AuthUtil.getUsername(request));
        return orderService.pageOrder(current, size, orderQueryVo);
    }

    @ApiOperation(value = "根据表id查询订单信息")
    @GetMapping("/get/{orderId}")
    public R getOrder(@PathVariable Integer orderId) {
        return orderService.getOrder(orderId);
    }

    @ApiOperation(value = "获取订单状态")
    @GetMapping("/listOrderStatus")
    public R listOrderStatus() {
        return orderService.listOrderStatus();
    }

    @ApiOperation(value = "取消订单")
    @GetMapping("/cancel/{orderId}")
    public R cancelOrder(@PathVariable Integer orderId) {
        return orderService.cancelOrder(orderId);
    }

    @ApiOperation(value = "获取订单统计数据")
    @PostMapping("/getOrderStatistics")
    public R getOrderStatistics(@RequestBody OrderCountQueryVo orderCountQueryVo) {
        return orderService.getOrderStatistics(orderCountQueryVo);
    }
}

