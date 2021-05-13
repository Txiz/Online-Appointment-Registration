package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.result.R;
import com.xzx.common.util.AuthUtil;
import com.xzx.hospital.mapper.DataDictionaryMapper;
import com.xzx.hospital.mapper.PatientInfoMapper;
import com.xzx.hospital.mapper.UserInfoMapper;
import com.xzx.hospital.service.PatientInfoService;
import com.xzx.model.entity.PatientInfo;
import com.xzx.model.entity.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 就诊人表 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-05-12
 */
@Service
public class PatientInfoServiceImpl extends ServiceImpl<PatientInfoMapper, PatientInfo> implements PatientInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public R savePatientInfo(PatientInfo patientInfo, HttpServletRequest request) {
        Integer userId = getUserIdFromRequest(request);
        patientInfo.setUserId(userId);
        return save(patientInfo) ? R.ok().message("保存就诊人信息成功！") : R.error().message("保存就诊人信息失败！");
    }

    @Override
    public R updatePatientInfo(PatientInfo patientInfo) {
        return updateById(patientInfo) ? R.ok().message("更新就诊人信息成功！") : R.error().message("更新就诊人信息失败！");
    }

    @Override
    public R getPatientInfo(Integer id) {
        return R.ok().data("patientInfo", setOtherParam(getById(id))).message("根据表id获取就诊人信息成功！");
    }

    @Override
    public R removePatientInfo(Integer id) {
        return removeById(id) ? R.ok().message("根据表id逻辑删除就诊人信息成功！") : R.error().message("根据表id逻辑删除就诊人信息失败！");
    }

    @Override
    public R listPatientInfo(HttpServletRequest request) {
        Integer userId = getUserIdFromRequest(request);
        QueryWrapper<PatientInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<PatientInfo> patientInfoList = list(wrapper);
        for (PatientInfo patientInfo : patientInfoList) {
            patientInfo = setOtherParam(patientInfo);
        }
        return R.ok().data("patientInfoList", patientInfoList).message("查询所有就诊人信息列表成功！");
    }

    private Integer getUserIdFromRequest(HttpServletRequest request) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", AuthUtil.getUsername(request));
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        return userInfo.getId();
    }

    private PatientInfo setOtherParam(PatientInfo patientInfo) {
        String certificatesTypeName = dataDictionaryMapper.getNameByValue(patientInfo.getCertificatesType());
        String contactsCertificatesTypeName = dataDictionaryMapper.getNameByValue(patientInfo.getContactsCertificatesType());
        String ProvinceName = dataDictionaryMapper.getNameByValue(patientInfo.getProvinceCode());
        String CityName = dataDictionaryMapper.getNameByValue(patientInfo.getCityCode());
        String DistrictName = dataDictionaryMapper.getNameByValue(patientInfo.getDistrictCode());
        patientInfo.getParam().put("certificatesTypeName", certificatesTypeName);
        patientInfo.getParam().put("contactsCertificatesTypeName", contactsCertificatesTypeName);
        patientInfo.getParam().put("provinceName", ProvinceName);
        patientInfo.getParam().put("cityName", CityName);
        patientInfo.getParam().put("districtName", DistrictName);
        patientInfo.getParam().put("fullAddress", ProvinceName + CityName + DistrictName + patientInfo.getAddress());
        return patientInfo;
    }
}
