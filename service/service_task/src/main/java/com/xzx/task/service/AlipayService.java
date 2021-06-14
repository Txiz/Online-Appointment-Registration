package com.xzx.task.service;

import com.xzx.common.result.R;

/**
 * 支付宝当面付服务接口
 * 作者: Txiz
 * 创建时间: 2021-06-14
 **/
public interface AlipayService {

    R pay(Integer orderId);

    R queryPayStatus(Integer orderId);

    R refund(Integer orderId);
}
