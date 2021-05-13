package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.PatientInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 就诊人表 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-05-12
 */
public interface PatientInfoService extends IService<PatientInfo> {

    R savePatientInfo(PatientInfo patientInfo, HttpServletRequest request);

    R updatePatientInfo(PatientInfo patientInfo);

    R getPatientInfo(Integer id);

    R removePatientInfo(Integer id);

    R listPatientInfo(HttpServletRequest request);
}
