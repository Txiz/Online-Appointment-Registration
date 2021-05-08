package com.xzx.hospital.repository;

import com.xzx.model.entity.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 工作计划操作
 * 作者: xzx
 * 创建时间: 2021-05-01-21-26
 **/
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    Schedule getScheduleByHospitalCodeAndHospitalScheduleId(String hospitalCode, String hospitalScheduleId);

    List<Schedule> findScheduleByHospitalCodeAndDepartmentCodeAndWorkDate(String hospitalCode, String departmentCode, Date toDate);
}
