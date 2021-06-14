package com.xzx.hospital.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.common.result.R;
import com.xzx.common.util.HttpRequestUtil;
import com.xzx.common.util.MD5Util;
import com.xzx.hospital.mapper.DataDictionaryMapper;
import com.xzx.hospital.mapper.HospitalSetMapper;
import com.xzx.hospital.repository.HospitalInfoRepository;
import com.xzx.hospital.service.HospitalInfoService;
import com.xzx.model.entity.HospitalInfo;
import com.xzx.model.entity.HospitalSet;
import com.xzx.model.vo.HospitalInfoQueryVo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 医院信息，服务实现
 * 作者: xzx
 * 创建时间: 2021-04-30-15-02
 **/
@Service
public class HospitalInfoServiceImpl implements HospitalInfoService {

    @Resource
    private HospitalSetMapper hospitalSetMapper;

    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Resource
    private HospitalInfoRepository hospitalInfoRepository;

    @Override
    public R saveHospitalInfo(HttpServletRequest request) {
        // 获取请求中的医院信息参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestUtil.switchMap(parameterMap);
        // 获取医院信息参数中的相关数据
        String hospitalSignKey = (String) map.get("sign");
        String hospitalCode = (String) map.get("hospitalCode");
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hospital_code", hospitalCode);
        if (hospitalSetMapper.selectCount(wrapper) <= 0) {
            return R.error().message("医院信息上传失败！医院编号不存在，请检查医院编号！");
        }
        if (!hospitalSignKey.equals(MD5Util.encrypt(hospitalSetMapper.getSignKey(hospitalCode)))) {
            return R.error().message("医院信息上传失败！签名不一致，请检查签名！");
        }
        String logo = (String) map.get("logoData");
        logo = logo.replaceAll(" ", "+");
        map.put("logoData", logo);
        save(map);
        return R.ok().message("医院信息上传成功！");
    }

    private void save(Map<String, Object> map) {
        // 将参数map转换为HospitalInfo对象
        String jsonString = JSONObject.toJSONString(map);
        HospitalInfo hospitalInfo = JSONObject.parseObject(jsonString, HospitalInfo.class);
        // 判断是否存在数据
        HospitalInfo info = hospitalInfoRepository.getHospitalInfoByHospitalCode(hospitalInfo.getHospitalCode());
        if (info != null) {
            // 不存在，则存在数据，更新操作
            hospitalInfo.setStatus(info.getStatus());
        } else {
            // 存在，则不存在数据，保存操作
            hospitalInfo.setStatus(0);
        }
        hospitalInfoRepository.save(hospitalInfo);
    }

    @Override
    public R getHospitalInfoByHospitalCode(HttpServletRequest request) {
        // 获取请求中的医院信息参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestUtil.switchMap(parameterMap);
        // 获取医院信息参数中的相关数据
        String hospitalCode = (String) map.get("hospitalCode");
        String hospitalSignKey = (String) map.get("sign");
        String signKey = hospitalSetMapper.getSignKey(hospitalCode);
        if (!hospitalSignKey.equals(MD5Util.encrypt(signKey))) {
            return R.error().message("医院信息查询失败！签名不一致，请检查签名！");
        }
        // 根据医院编号从数据库中获取数据
        HospitalInfo hospitalInfo = hospitalInfoRepository.getHospitalInfoByHospitalCode(hospitalCode);
        return R.ok().data("hospitalInfo", hospitalInfo).message("医院信息查询成功！");
    }

    @Override
    public R pageHospitalInfo(Integer current, Integer size, HospitalInfoQueryVo hospitalInfoQueryVo) {
        // 创建分页请求
        Pageable pageable = PageRequest.of(current - 1, size);
        // 创建条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        // 查询视图对象转换为医院信息对象
        HospitalInfo hospitalInfo = new HospitalInfo();
        if (!ObjectUtils.isEmpty(hospitalInfoQueryVo))
            BeanUtils.copyProperties(hospitalInfoQueryVo, hospitalInfo);
        // 创建条件对象
        Example<HospitalInfo> example = Example.of(hospitalInfo, matcher);
        // 调用方法实现分页查询
        Page<HospitalInfo> page = hospitalInfoRepository.findAll(example, pageable);
        // 获取查询的列表，设置医院信息的其他参数
        page.getContent().forEach(this::setHospitalType);
        return R.ok().data("hospitalInfoList", page).data("totalPage", page.getTotalPages()).message("分页查询所有医院信息列表成功！");
    }

    private void setHospitalType(HospitalInfo hospitalInfo) {
        // 获取医院等级名称
        String typeName = dataDictionaryMapper.getNameByValue(hospitalInfo.getHospitalType());
        // 获取省市区
        String province = dataDictionaryMapper.getNameByValue(hospitalInfo.getProvinceCode());
        String city = dataDictionaryMapper.getNameByValue(hospitalInfo.getCityCode());
        String district = dataDictionaryMapper.getNameByValue(hospitalInfo.getDistrictCode());
        // 设置其他参数
        hospitalInfo.getParameter().put("fullAddress", province + city + district);
        hospitalInfo.getParameter().put("hospitalTypeName", typeName);
    }

    @Override
    public R updateStatus(String id, Integer status) {
        // 根据表id查询医院信息
        HospitalInfo hospitalInfo = hospitalInfoRepository.findById(id).get();
        // 修改状态
        hospitalInfo.setStatus(status);
        hospitalInfoRepository.save(hospitalInfo);
        return R.ok().message("更新医院状态成功！");
    }

    @Override
    public R getHospitalInfo(String id) {
        // 根据表id查询医院信息
        HospitalInfo hospitalInfo = hospitalInfoRepository.findById(id).get();
        // 设置医院信息的其他参数
        setHospitalType(hospitalInfo);
        return R.ok().data("hospitalInfo", hospitalInfo).message("根据表id获取医院信息成功！");
    }

    @Override
    public R listByHospitalName(String hospitalName) {
        List<HospitalInfo> hospitalInfoList = hospitalInfoRepository.findHospitalInfoByHospitalNameLike(hospitalName);
        return R.ok().data("hospitalInfo", hospitalInfoList).message("根据医院名称获取医院信息列表成功！");
    }
}
