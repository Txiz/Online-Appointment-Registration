package com.xzx.imitate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.util.RandomUtil;
import com.xzx.imitate.entity.Order;
import com.xzx.imitate.entity.Stock;
import com.xzx.imitate.mapper.OrderMapper;
import com.xzx.imitate.mapper.StockMapper;
import com.xzx.imitate.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-05-31
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private StockMapper stockMapper;

    @Transactional
    @Override
    public Map<String, Object> createOrder(Integer sid) {
        // 判断订单ID是否存在
        if (sid == null) throw new RuntimeException("订单ID为空");
        // 检查库存，判断是否卖空
        Stock stock = stockMapper.selectByIdForUpdate(sid);
        if (stock.getSale().equals(stock.getCount())) throw new RuntimeException("库存不足");
        // 扣除库存
        stock.setSale(stock.getSale() + 1);
        if (stockMapper.updateById(stock) < 1) throw new RuntimeException("更新失败");
        //创建订单
        Order order = new Order();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        order.setUid(1);
        order.setCreateTime(new Date());
        save(order);
        // 结果集
        Map<String, Object> map = new HashMap<>();
        map.put("id", order.getId());
        map.put("reservedNumber", stock.getCount());
        map.put("availableNumber", stock.getCount() - stock.getSale());
        map.put("number", RandomUtil.getFourBitRandom());
        map.put("fetchTime", "测试时间");
        map.put("fetchAddress", "测试地址");
        map.put("quitTime", new Date());
        return map;
    }
}
