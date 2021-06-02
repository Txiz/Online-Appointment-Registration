package com.xzx.imitate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzx.imitate.entity.Stock;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xzx
 * @since 2021-05-31
 */
public interface StockMapper extends BaseMapper<Stock> {

    Stock selectByIdForUpdate(int sid);
}
