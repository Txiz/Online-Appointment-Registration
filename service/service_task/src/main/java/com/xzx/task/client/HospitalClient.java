package com.xzx.task.client;

import com.xzx.common.config.FeignConfig;
import com.xzx.common.result.R;
import com.xzx.model.entity.Statistics;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 服务调用
 * 作者: Txiz
 * 创建时间: 2021-05-21-13-59
 **/
@FeignClient(name = "service-hospital", configuration = FeignConfig.class)
@Component
public interface HospitalClient {

    @ApiOperation(value = "保存统计数据")
    @PostMapping("/hospital/statistics/save")
    R saveStatistics(@RequestBody Statistics statistics);

    @ApiOperation(value = "根据表id查询订单信息")
    @GetMapping("/hospital/order/get/{orderId}")
    R getOrder(@PathVariable("orderId") Integer orderId);
}
