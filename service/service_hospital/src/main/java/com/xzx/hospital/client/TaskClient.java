package com.xzx.hospital.client;

import com.xzx.common.config.FeignConfig;
import com.xzx.common.result.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 服务调用
 * 作者: Txiz
 * 创建时间: 2021-06-14
 **/
@FeignClient(name = "service-task", configuration = FeignConfig.class)
@Component
public interface TaskClient {

    @ApiOperation("订单取消退款")
    @GetMapping("/task/alipay/refund/{orderId}")
    R refund(@PathVariable Integer orderId);
}
