package com.xzx.imitate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.imitate.entity.Order;
import com.xzx.imitate.entity.Stock;
import com.xzx.imitate.mapper.OrderMapper;
import com.xzx.imitate.mapper.StockMapper;
import com.xzx.imitate.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Override
    public int createOrder1(int sid) {
        // 检查库存，判断是否卖空
        Stock stock = stockMapper.selectById(sid);
        if (stock.getSale().equals(stock.getCount())) throw new RuntimeException("库存不足");
        // 扣除库存
        stock.setSale(stock.getSale() + 1);
        if (stockMapper.updateById(stock) < 1) throw new RuntimeException("乐观锁更新失败");
        //创建订单
        Order order = new Order();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        save(order);
        return order.getId();
    }

    @Transactional
    @Override
    public int createOrder2(int sid) {
        // 检查库存，判断是否卖空
        Stock stock = stockMapper.selectByIdForUpdate(sid);
        if (stock.getSale().equals(stock.getCount())) throw new RuntimeException("库存不足");
        // 扣除库存
        stock.setSale(stock.getSale() + 1);
        if (stockMapper.updateById(stock) < 1) throw new RuntimeException("乐观锁更新失败");
        //创建订单
        Order order = new Order();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        order.setUid(1);
        save(order);
        return order.getId();
    }
}
