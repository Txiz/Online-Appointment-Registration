package com.xzx.hospital.repository;

import com.xzx.model.entity.HospitalInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 医院信息操作
 * 作者: xzx
 * 创建时间: 2021-04-30-14-59
 **/
@Repository
public interface HospitalInfoRepository extends MongoRepository<HospitalInfo, String> {

    HospitalInfo getHospitalInfoByHospitalCode(String hospitalCode);
}
