package com.xzx.hospital.mapper;

import com.xzx.model.entity.HospitalSet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 医院集合表 Mapper 接口
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
public interface HospitalSetMapper extends BaseMapper<HospitalSet> {

    String getSignKey(String hospitalCode);

    String getHospitalNameByHospitalCode(String hospitalCode);
}
