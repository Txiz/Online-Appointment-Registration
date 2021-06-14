package com.xzx.hospital.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzx.common.result.R;
import com.xzx.common.util.HttpRequestUtil;
import com.xzx.common.util.MD5Util;
import com.xzx.hospital.mapper.HospitalSetMapper;
import com.xzx.hospital.repository.DepartmentRepository;
import com.xzx.hospital.repository.HospitalInfoRepository;
import com.xzx.hospital.repository.ScheduleRepository;
import com.xzx.hospital.service.ScheduleService;
import com.xzx.model.entity.*;
import com.xzx.model.vo.ScheduleRuleVo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

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
    private HospitalInfoRepository hospitalInfoRepository;

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

    @Override
    public R getBookingSchedule(Integer current, Integer size, String hospitalCode, String departmentCode) {
        //获取预约规则
        //根据医院编号获取预约规则
        HospitalInfo hospitalInfo = hospitalInfoRepository.getHospitalInfoByHospitalCode(hospitalCode);
        if (hospitalInfo == null) {
            return R.error().message("编号：" + hospitalCode + " 医院信息为空！");
        }
        BookingRule bookingRule = hospitalInfo.getBookingRule();
        IPage<Date> iPage = listDate(current, size, bookingRule);
        //获取可预约日期的数据（分页）
        List<Date> dateList = iPage.getRecords();
        //获取可预约日期里面科室的剩余预约数
        Criteria criteria = Criteria.where("hospitalCode").is(hospitalCode).and("departmentCode").is(departmentCode)
                .and("workDate").in(dateList);
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate").first("workDate").as("workDate")
                        .count().as("doctorCount")
                        .sum("availableNumber").as("availableNumber")
                        .sum("reservedNumber").as("reservedNumber")
        );
        AggregationResults<ScheduleRuleVo> aggregateResult =
                mongoTemplate.aggregate(agg, Schedule.class, ScheduleRuleVo.class);
        List<ScheduleRuleVo> scheduleVoList = aggregateResult.getMappedResults();
        //合并数据 map集合 key日期 value预约规则和剩余数量等
        Map<Date, ScheduleRuleVo> scheduleVoMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(scheduleVoList)) {
            scheduleVoMap = scheduleVoList.stream().collect(Collectors.toMap(ScheduleRuleVo::getWorkDate, BookingScheduleRuleVo -> BookingScheduleRuleVo));
        }
        //获取可预约排班规则
        List<ScheduleRuleVo> bookingScheduleRuleVoList = new ArrayList<>();
        for (int i = 0, len = dateList.size(); i < len; i++) {
            Date date = dateList.get(i);
            //从map集合根据key日期获取value值
            ScheduleRuleVo bookingScheduleRuleVo = scheduleVoMap.get(date);
            //如果当天没有排班医生
            if (bookingScheduleRuleVo == null) {
                bookingScheduleRuleVo = new ScheduleRuleVo();
                //就诊医生人数
                bookingScheduleRuleVo.setDoctorCount(0);
                //科室剩余预约数 -1表示无号
                bookingScheduleRuleVo.setAvailableNumber(-1);
            }
            bookingScheduleRuleVo.setWorkDate(date);
            bookingScheduleRuleVo.setWorkDateMD(date);
            // 计算当前预约日期对应星期
            String dayOfWeek = this.getDayOfWeek(new DateTime(date));
            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);
            // 最后一页最后一条记录为即将预约   状态 0：正常 1：即将放号 -1：当天已停止挂号
            if (i == len - 1 && current == iPage.getPages()) bookingScheduleRuleVo.setStatus(1);
            else bookingScheduleRuleVo.setStatus(0);
            // 当天预约如果过了停号时间， 不能预约
            if (i == 0 && current == 1) {
                DateTime stopTime = getDateTime(new Date(), bookingRule.getStopTime());
                // 停止预约
                if (stopTime.isBeforeNow()) bookingScheduleRuleVo.setStatus(-1);
            }
            bookingScheduleRuleVoList.add(bookingScheduleRuleVo);
        }
        // 可预约日期规则数据
        Map<String, Object> result = new HashMap<>();
        result.put("scheduleList", bookingScheduleRuleVoList);
        result.put("total", iPage.getTotal());
        Map<String, String> param = new HashMap<>();
        param.put("hospitalName", hospitalSetMapper.getHospitalNameByHospitalCode(hospitalCode));
        Department department = departmentRepository.getDepartmentByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
        param.put("bigName", department.getBigName());
        param.put("departmentName", department.getDepartmentName());
        param.put("workDateString", new DateTime().toString("yyyy年MM月"));
        param.put("releaseTime", bookingRule.getReleaseTime());
        param.put("stopTime", bookingRule.getStopTime());
        result.put("param", param);
        return R.ok().data(result).message("根据医院编号和科室编号分页查询可预约排班规则成功！");
    }

    private IPage<Date> listDate(Integer current, Integer size, BookingRule bookingRule) {
        //获取当天放号时间  年 月 日 小时 分钟
        DateTime releaseTime = this.getDateTime(new Date(), bookingRule.getReleaseTime());
        //获取预约周期
        Integer cycle = bookingRule.getCycle();
        //如果当天放号时间已经过去了，预约周期从后一天开始计算，周期+1
        if (releaseTime.isBeforeNow()) {
            cycle += 1;
        }
        //获取可预约所有日期，最后一天显示即将放号
        List<Date> dateList = new ArrayList<>();
        for (int i = 0; i < cycle; i++) {
            DateTime curDateTime = new DateTime().plusDays(i);
            String dateString = curDateTime.toString("yyyy-MM-dd");
            dateList.add(new DateTime(dateString).toDate());
        }
        //因为预约周期不同的，每页显示日期最多7天数据，超过7天分页
        List<Date> pageDateList = new ArrayList<>();
        int start = (current - 1) * size;
        int end = (current - 1) * size + size;
        //如果可以显示数据小于7，直接显示
        if (end > dateList.size()) {
            end = dateList.size();
        }
        for (int i = start; i < end; i++) {
            pageDateList.add(dateList.get(i));
        }
        //如果可以显示数据大于7，进行分页
        IPage<Date> iPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(current, 7, dateList.size());
        iPage.setRecords(pageDateList);
        return iPage;
    }

    /**
     * 将Date日期（yyyy-MM-dd HH:mm）转换为DateTime
     */
    private DateTime getDateTime(Date date, String timeString) {
        String dateTimeString = new DateTime(date).toString("yyyy-MM-dd") + " " + timeString;
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").parseDateTime(dateTimeString);
    }
}
