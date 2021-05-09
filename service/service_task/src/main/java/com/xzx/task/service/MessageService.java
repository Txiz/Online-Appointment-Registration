package com.xzx.task.service;

import com.xzx.common.result.R;

/**
 * 短信验证，服务接口
 * 作者: xzx
 * 创建时间: 2021-05-08-15-41
 **/
public interface MessageService {

    R getCode(String phone);
}
