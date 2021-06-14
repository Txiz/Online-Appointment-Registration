package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.result.R;
import com.xzx.common.util.MD5Util;
import com.xzx.hospital.mapper.HospitalSetMapper;
import com.xzx.hospital.service.HospitalSetService;
import com.xzx.model.entity.HospitalSet;
import com.xzx.model.vo.HospitalSetQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 医院集合表 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    @Override
    public R saveHospitalSet(HospitalSet hospitalSet) {
        // 设置医院集合状态
        hospitalSet.setStatus(1);
        // 设置签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5Util.encrypt(System.currentTimeMillis() + "" + random.nextInt(100)));
        return save(hospitalSet) ? R.ok().message("保存医院集合成功！") : R.error().message("保存医院集合失败！");
    }

    @Override
    public R updateHospitalSet(HospitalSet hospitalSet) {
        return updateById(hospitalSet) ? R.ok().message("更新医院集合成功！") : R.error().message("更新医院集合失败！");
    }

    @Override
    public R getHospitalSet(Integer id) {
        return R.ok().data("hospitalSet", getById(id)).message("根据表id获取医院集合成功！");
    }

    @Override
    public R removeHospitalSet(Integer id) {
        return removeById(id) ? R.ok().message("根据表id逻辑删除医院集合成功！") : R.error().message("根据表id逻辑删除医院集合失败！");
    }

    @Override
    public R listHospitalSet() {
        return R.ok().data("hospitalSetList", list()).message("查询所有医院集合列表成功！");
    }

    @Override
    public R pageHospitalSet(Integer current, Integer size, HospitalSetQueryVo hospitalSetQueryVo) {
        // 获取查询参数
        String hospitalName = hospitalSetQueryVo.getHospitalName();
        String hospitalCode = hospitalSetQueryVo.getHospitalCode();
        // 构造分页对象
        Page<HospitalSet> hospitalSetPage = new Page<>(current, size);
        // 构造查询条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(hospitalName)) wrapper.like("hospital_name", hospitalName);
        if (!StringUtils.isEmpty(hospitalCode)) wrapper.eq("hospital_code", hospitalCode);
        // 调用分页方法
        page(hospitalSetPage, wrapper);
        // 封装结果集
        Map<String, Object> map = new HashMap<>();
        map.put("total", hospitalSetPage.getTotal());
        map.put("hospitalSetPageList", hospitalSetPage.getRecords());
        return R.ok().data(map).message("分页查询所有医院集合列表成功！");
    }

    @Override
    public R lockHospitalSet(Integer id, Integer status) {
        // 保证乐观锁起效，必须先获取当前version
        HospitalSet hospitalSet = getById(id);
        hospitalSet.setStatus(status);
        updateById(hospitalSet);
        return R.ok().message("跟据表id锁定医院集合成功！");
    }

    @Override
    public R getSignKeyById(Integer id) {
        HospitalSet hospitalSet = getById(id);
        String signKey = hospitalSet.getSignKey();
        String hospitalCode = hospitalSet.getHospitalCode();
        return R.ok();
    }
}
