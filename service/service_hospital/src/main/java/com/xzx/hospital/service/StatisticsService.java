package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.Statistics;
import com.xzx.model.vo.StatisticsQueryVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-04-12
 */
public interface StatisticsService extends IService<Statistics> {

    R saveStatistics(Statistics statistics);

    R pageStatistics(Integer current, Integer size, StatisticsQueryVo statisticsQueryVo);

    R removeStatistics(Integer statisticsId);

    R getStatisticsDataForMap();

    R getCurrentStatisticsNum();
}
