package com.xzx.hospital.repository;

import com.xzx.model.entity.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 部门科室操作
 * 作者: xzx
 * 创建时间: 2021-05-01-15-31
 **/
@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

    Department getDepartmentByHospitalCodeAndDepartmentCode(String hospitalCode, String departmentCode);
}
