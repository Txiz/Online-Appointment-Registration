package com.xzx.hospital.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzx.common.result.R;
import com.xzx.common.util.HttpRequestUtil;
import com.xzx.common.util.MD5Util;
import com.xzx.hospital.mapper.HospitalSetMapper;
import com.xzx.hospital.repository.DepartmentRepository;
import com.xzx.hospital.service.DepartmentService;
import com.xzx.model.entity.Department;
import com.xzx.model.entity.HospitalSet;
import com.xzx.model.vo.DepartmentQueryVo;
import com.xzx.model.vo.DepartmentVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门科室，服务实现
 * 作者: xzx
 * 创建时间: 2021-05-01-15-15
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private HospitalSetMapper hospitalSetMapper;

    @Resource
    private DepartmentRepository departmentRepository;

    @Override
    public R saveDepartment(HttpServletRequest request) {
        // 获取请求中的部门科室参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestUtil.switchMap(parameterMap);
        // 获取部门科室参数中的相关数据
        String hospitalSignKey = (String) map.get("sign");
        String hospitalCode = (String) map.get("hospitalCode");
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hospital_code", hospitalCode);
        if (hospitalSetMapper.selectCount(wrapper) <= 0) {
            return R.error().message("部门科室上传失败！医院编号不存在，请检查医院编号！");
        }
        if (!hospitalSignKey.equals(MD5Util.encrypt(hospitalSetMapper.getSignKey(hospitalCode)))) {
            return R.error().message("部门科室上传失败！签名不一致，请检查签名！");
        }
        save(map);
        return R.ok().message("部门科室上传成功！");
    }

    private void save(Map<String, Object> map) {
        // 将参数map转换为Department对象
        String jsonString = JSONObject.toJSONString(map);
        Department department = JSONObject.parseObject(jsonString, Department.class);
        // 判断是否存在数据
        Department info = departmentRepository.
                getDepartmentByHospitalCodeAndDepartmentCode(department.getHospitalCode(), department.getDepartmentCode());
        // TODO 更新未完成
        departmentRepository.save(department);
    }

    @Override
    public R getDepartmentByHospitalCode(HttpServletRequest request) {
        // 获取请求中的部门科室参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestUtil.switchMap(parameterMap);
        // 获取部门科室参数中的相关数据
        String hospitalCode = (String) map.get("hospitalCode");
        String currentStr = (String) map.get("current");
        int current = StringUtils.isEmpty(currentStr) ? 1 : Integer.parseInt(currentStr);
        String sizeStr = (String) map.get("size");
        int size = StringUtils.isEmpty(sizeStr) ? 1 : Integer.parseInt(sizeStr);
        // 构建查询参数
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHospitalCode(hospitalCode);
        return pageDepartment(current, size, departmentQueryVo);
    }

    @Override
    public R removeDepartment(HttpServletRequest request) {
        // 获取请求中的部门科室参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestUtil.switchMap(parameterMap);
        // 获取部门科室参数中的相关数据
        String hospitalCode = (String) map.get("hospitalCode");
        String departmentCode = (String) map.get("departmentCode");
        // TODO 签名校验
        // 判断是否存在数据
        Department info = departmentRepository.getDepartmentByHospitalCodeAndDepartmentCode(hospitalCode, departmentCode);
        // 如果存在就根据表id删除
        if (info != null)
            departmentRepository.deleteById(info.getId());
        return R.ok().message("根据医院编号和科室编号删除部门科室成功！");
    }

    @Override
    public R listDepartmentVo(String hospitalCode) {
        // 根据医院编号，查询医院下的所有部门科室信息
        Department department = new Department();
        department.setHospitalCode(hospitalCode);
        Example<Department> example = Example.of(department);
        // 部门科室列表
        List<Department> departmentList = departmentRepository.findAll(example);
        // 根据大科室编号进行分组，构建部门科室列表树
        Map<String, List<Department>> map = departmentList.stream().collect(Collectors.groupingBy(Department::getBigCode));
        // 构建结果集
        List<DepartmentVo> res = new ArrayList<>();
        // 遍历map
        for (Map.Entry<String, List<Department>> entry : map.entrySet()) {
            // 大科室编号及其对应的数据
            String bigCode = entry.getKey();
            List<Department> departments = entry.getValue();
            // 封装科室编号视图
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepartmentCode(bigCode);
            departmentVo.setDepartmentName(departments.get(0).getBigName());
            List<DepartmentVo> children = new ArrayList<>();
            for (Department d : departments) {
                DepartmentVo vo = new DepartmentVo();
                vo.setDepartmentCode(d.getDepartmentCode());
                vo.setDepartmentName(d.getDepartmentName());
                children.add(vo);
            }
            departmentVo.setChildren(children);
            res.add(departmentVo);
        }
        return R.ok().message("根据医院编号查询所有的部门科室成功！").data("departmentList", res);
    }

    public R pageDepartment(Integer current, Integer size, DepartmentQueryVo departmentQueryVo) {
        // 创建分页请求
        Pageable pageable = PageRequest.of(current - 1, size);
        // 创建条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        // 查询视图对象转换为医院信息对象
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        // 创建条件对象
        Example<Department> example = Example.of(department, matcher);
        // 调用方法实现分页查询
        Page<Department> page = departmentRepository.findAll(example, pageable);
        return R.ok().data("departmentList", page).message("分页查询所有部门科室列表成功！");
    }
}
