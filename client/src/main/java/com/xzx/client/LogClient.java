package com.xzx.client;

import com.xzx.common.config.FeignConfig;
import com.xzx.common.result.R;
import com.xzx.model.entity.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Log
 * 作者: xzx
 * 创建时间: 2021-05-14-15-26
 **/
@FeignClient(name = "service-task", configuration = FeignConfig.class)
@Component
public interface LogClient {

    @PostMapping("/task/log/save")
    R saveLog(@RequestBody Log log);
}
