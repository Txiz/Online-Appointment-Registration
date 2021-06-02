package com.xzx.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzx.model.entity.Order;
import com.xzx.model.vo.OrderCountQueryVo;
import com.xzx.model.vo.OrderCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author xzx
 * @since 2021-06-02
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<OrderCountVo> getOrderStatistics(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
