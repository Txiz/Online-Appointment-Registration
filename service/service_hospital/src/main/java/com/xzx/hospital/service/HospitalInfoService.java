package com.xzx.hospital.service;

import com.xzx.common.result.R;
import com.xzx.model.vo.HospitalInfoQueryVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 医院信息表 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
public interface HospitalInfoService {

    R saveHospitalInfo(HttpServletRequest request);

    R getHospitalInfoByHospitalCode(HttpServletRequest request);

    R pageHospitalInfo(Integer current, Integer size, HospitalInfoQueryVo hospitalInfoQueryVo);

    R updateStatus(String id, Integer status);

    R getHospitalInfo(String id);

    R listByHospitalName(String hospitalName);
}
