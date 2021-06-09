package com.xzx.imitate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.imitate.entity.Order;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-05-31
 */
public interface OrderService extends IService<Order> {

    Map<String, Object> createOrder(Integer sid);
}
