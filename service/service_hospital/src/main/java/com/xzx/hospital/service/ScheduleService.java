package com.xzx.hospital.service;

import com.xzx.common.result.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 工作计划，服务接口
 * 作者: xzx
 * 创建时间: 2021-05-01-20-23
 **/
public interface ScheduleService {

    R saveSchedule(HttpServletRequest request);

    R getScheduleByHospitalCodeAndDepartmentCode(HttpServletRequest request);

    R removeSchedule(HttpServletRequest request);

    R getScheduleRule(Integer current, Integer size, String hospitalCode, String departmentCode);

    R getSchedule(String hospitalCode, String departmentCode, String workDate);

    R getBookingSchedule(Integer current, Integer size, String hospitalCode, String departmentCode);
}
