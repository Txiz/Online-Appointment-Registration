package com.xzx.hospital.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.common.result.R;
import com.xzx.common.util.HttpRequestUtil;
import com.xzx.common.util.MD5Util;
import com.xzx.hospital.mapper.HospitalSetMapper;
import com.xzx.hospital.repository.DepartmentRepository;
import com.xzx.hospital.repository.ScheduleRepository;
import com.xzx.hospital.service.ScheduleService;
import com.xzx.model.entity.HospitalSet;
import com.xzx.model.entity.Schedule;
import com.xzx.model.vo.ScheduleRuleVo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作计划，服务实现
 * 作者: xzx
 * 创建时间: 2021-05-01-20-24
 **/
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private HospitalSetMapper hospitalSetMapper;

    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private ScheduleRepository scheduleRepository;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public R saveSchedule(HttpServletRequest request) {
        // 获取请求中的工作计划参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestUtil.switchMap(parameterMap);
        // 获取工作计划参数中的相关数据
        String hospitalSignKey = (String) map.get("sign");
        String hospitalCode = (String) map.get("hospitalCode");
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hospital_code", hospitalCode);
        if (hospitalSetMapper.selectCount(wrapper) <= 0) {
            return R.error().message("工作计划上传失败！医院编号不存在，请检查医院编号！");
        }
        if (!hospitalSignKey.equals(MD5Util.encrypt(hospitalSetMapper.getSignKey(hospitalCode)))) {
            return R.error().message("工作计划上传失败！签名不一致，请检查签名！");
        }
        save(map);
        return R.ok().message("工作计划上传成功！");
    }

    private void save(Map<String, Object> map) {
        // 将参数map转换为Schedule对象
        String jsonString = JSONObject.toJSONString(map);
        Schedule schedule = JSONObject.parseObject(jsonString, Schedule.class);
        // 判断是否存在数据
        Schedule info = scheduleRepository.
                getScheduleByHospitalCodeAndHospitalScheduleId(schedule.getHospitalCode(), schedule.getHospitalScheduleId());
        // TODO 更新未完成
        scheduleRepository.save(schedule);
    }

    @Override
    public R getScheduleByHospitalCodeAndDepartmentCode(HttpServletRequest request) {
        return null;
    }

    @Override
    public R removeSchedule(HttpServletRequest request) {
        return null;
    }

    @Override
    public R getScheduleRule(Integer current, Integer size, String hospitalCode, String departmentCode) {
        // 根据医院编号和科室编号查询
        Criteria criteria = Criteria.where("hospitalCode").is(hospitalCode).and("departmentCode").is(departmentCode);
        // 根据工作日期进行分组
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate").first("workDate").as("workDate")
                        // 统计号源数量
                        .count().as("doctorCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                // 排序
                Aggregation.sort(Sort.Direction.DESC, "workDate"),
                // 实现分页
                Aggregation.skip((current - 1) * size),
                Aggregation.limit(size)
        );
        // 调用方法，执行
        AggregationResults<ScheduleRuleVo> a1 = mongoTemplate.aggregate(aggregation, Schedule.class, ScheduleRuleVo.class);
        List<ScheduleRuleVo> scheduleRuleVoList = a1.getMappedResults();
        // 分页查询总记录数
        Aggregation totalAggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );
        AggregationResults<ScheduleRuleVo> a2 = mongoTemplate.aggregate(totalAggregation, Schedule.class, ScheduleRuleVo.class);
        int total = a2.getMappedResults().size();
        // 把日期对应星期获取
        for (ScheduleRuleVo vo : scheduleRuleVoList) {
            Date workDate = vo.getWorkDate();
            String dayOfWeek = getDayOfWeek(new DateTime(workDate));
            vo.setDayOfWeek(dayOfWeek);
        }
        // 设置结果集
        Map<String, String> parameter = new HashMap<>();
        parameter.put("hospitalName", hospitalSetMapper.getHospitalNameByHospitalCode(hospitalCode));
        Map<String, Object> map = new HashMap<>();
        map.put("scheduleRuleVoList", scheduleRuleVoList);
        map.put("total", total);
        map.put("parameter", parameter);
        return R.ok().data(map).message("根据医院编号和科室编号分页查询排班规则成功！");
    }

    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "周日";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "周一";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "周二";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "周三";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "周四";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "周五";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "周六";
            default:
                break;
        }
        return dayOfWeek;
    }

    @Override
    public R getSchedule(String hospitalCode, String departmentCode, String workDate) {
        // 根据参数查询mongodb
        List<Schedule> scheduleList = scheduleRepository.findScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(hospitalCode, departmentCode, new DateTime(workDate).toDate());
        // 设置其他值：医院名称、科室名称、日期对应星期
        scheduleList.forEach(item -> {
            item.getParameter().put("hospitalName", hospitalSetMapper.getHospitalNameByHospitalCode(item.getHospitalCode()));
            item.getParameter().put("departmentName", departmentRepository.getDepartmentByHospitalCodeAndDepartmentCode(item.getHospitalCode(), item.getDepartmentCode()).getDepartmentName());
            item.getParameter().put("dayOfWeek", getDayOfWeek(new DateTime(item.getWorkDate())));
        });
        return R.ok().data("scheduleList", scheduleList).message("根据医院编号、科室编号和工作日期查询工作计划成功！");
    }
}
