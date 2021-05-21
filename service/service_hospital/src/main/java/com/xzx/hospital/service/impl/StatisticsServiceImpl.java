package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.result.R;
import com.xzx.hospital.mapper.StatisticsMapper;
import com.xzx.hospital.service.StatisticsService;
import com.xzx.model.entity.Statistics;
import com.xzx.model.vo.StatisticsQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-04-12
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public R saveStatistics(Statistics statistics) {
        return save(statistics) ? R.ok().message("保存统计数据成功！") : R.error().message("保存统计数据失败！");
    }

    @Override
    public R pageStatistics(Integer current, Integer size, StatisticsQueryVo statisticsQueryVo) {
        String beginTime = statisticsQueryVo.getBeginTime();
        String endTime = statisticsQueryVo.getEndTime();
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(beginTime)) wrapper.ge("create_time", beginTime);
        if (!StringUtils.isEmpty(endTime)) wrapper.le("create_time", endTime);
        Page<Statistics> statisticsPage = new Page<>(current, size);
        page(statisticsPage, wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", statisticsPage.getTotal());
        map.put("statisticsList", statisticsPage.getRecords());
        return R.ok().data(map).message("分页查询统计数据成功！");
    }

    @Override
    public R removeStatistics(Integer statisticsId) {
        return removeById(statisticsId) ? R.ok().message("统计数据删除成功!") : R.error().message("统计数据删除失败!");
    }

    @Override
    public R getStatisticsDataForMap() {
        QueryWrapper<Statistics> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("statistics_date");
        wrapper.last("limit 14");
        List<Statistics> list = list(wrapper);
        List<String> date = new ArrayList<>();
        List<Integer> loginNums = new ArrayList<>();
        List<Integer> registerNums = new ArrayList<>();
        List<Integer> exceptionNums = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            date.add(list.get(i).getStatisticsDate());
            loginNums.add(list.get(i).getLoginNum());
            registerNums.add(list.get(i).getRegisterNum());
            exceptionNums.add(list.get(i).getExceptionNum());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("loginNums", loginNums);
        map.put("registerNums", registerNums);
        map.put("exceptionNums", exceptionNums);
        return R.ok().data(map).message("获取地图数据成功");
    }

    @Override
    public R getCurrentStatisticsNum() {
        Integer loginNum = (Integer) redisTemplate.opsForValue().get("login_num");
        Integer exceptionNum = (Integer) redisTemplate.opsForValue().get("exception_num");
        Integer registerNum = (Integer) redisTemplate.opsForValue().get("register_num");
        // 判断是否是null
        if (loginNum == null) loginNum = 0;
        if (exceptionNum == null) exceptionNum = 0;
        if (registerNum == null) registerNum = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("loginNum", loginNum);
        map.put("registerNum", registerNum);
        map.put("exceptionNum", exceptionNum);
        return R.ok().data(map).message("从redis中获取当日临时统计数据成功!");
    }
}
