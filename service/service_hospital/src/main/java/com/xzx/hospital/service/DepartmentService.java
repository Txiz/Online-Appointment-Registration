package com.xzx.hospital.service;

import com.xzx.common.result.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 部门科室，服务接口
 * 作者: xzx
 * 创建时间: 2021-05-01-15-15
 **/
public interface DepartmentService {
    R saveDepartment(HttpServletRequest request);

    R getDepartmentByHospitalCode(HttpServletRequest request);

    R removeDepartment(HttpServletRequest request);

    R listDepartmentVo(String hospitalCode);
}
