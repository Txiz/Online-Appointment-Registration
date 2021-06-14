package com.xzx.hospital.client;

import com.xzx.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 服务调用
 * 作者: Txiz
 * 创建时间: 2021-06-14
 **/
@FeignClient(name = "service-task", configuration = FeignConfig.class)
@Component
public class TaskClient {

}
