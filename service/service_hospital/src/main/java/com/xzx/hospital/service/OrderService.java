package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.Order;
import com.xzx.model.vo.OrderCountQueryVo;
import com.xzx.model.vo.OrderQueryVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-06-02
 */
public interface OrderService extends IService<Order> {

    R saveOrder(String scheduleId, Integer patientId);

    R pageOrder(Integer current, Integer size, OrderQueryVo orderQueryVo);

    R getOrder(Integer orderId);

    R listOrderStatus();

    R cancelOrder(Integer orderId);

    R getOrderStatistics(OrderCountQueryVo orderCountQueryVo);
}
