package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.HospitalSet;
import com.xzx.model.vo.HospitalSetQueryVo;

/**
 * 医院信息，服务接口
 * 作者: xzx
 * 创建时间: 2021-04-30-15-02
 **/
public interface HospitalSetService extends IService<HospitalSet> {

    R saveHospitalSet(HospitalSet hospitalSet);

    R updateHospitalSet(HospitalSet hospitalSet);

    R getHospitalSet(Integer id);

    R removeHospitalSet(Integer id);

    R listHospitalSet();

    R pageHospitalSet(Integer current, Integer size, HospitalSetQueryVo hospitalSetQueryVo);

    R lockHospitalSet(Integer id, Boolean status);

    R getSignKeyById(Integer id);
}
