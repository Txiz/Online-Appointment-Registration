package com.xzx.hospital.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.result.R;
import com.xzx.hospital.listener.DataDictionaryListener;
import com.xzx.hospital.mapper.DataDictionaryMapper;
import com.xzx.hospital.service.DataDictionaryService;
import com.xzx.model.entity.DataDictionary;
import com.xzx.model.vo.DataDictionaryExcelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
@Service
public class DataDictionaryServiceImpl extends ServiceImpl<DataDictionaryMapper, DataDictionary> implements DataDictionaryService {

    @Override
    public R importDataDictionary(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DataDictionaryExcelVo.class, new DataDictionaryListener()).sheet().doRead();
            return R.ok().message("Excel表导入成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return R.ok().message("Excel表导入失败！发生IO异常");
        }
    }

    @Override
    public R exportDataDictionary(HttpServletResponse response) {
        // 设置下载信息
        String fileName = "DataDictionary";
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        // 查询数据库
        List<DataDictionary> dictionaries = list();
        // 构造数据字典转Excel视图
        List<DataDictionaryExcelVo> excelVoList = new ArrayList<>();
        for (DataDictionary dictionary : dictionaries) {
            DataDictionaryExcelVo excelVo = new DataDictionaryExcelVo();
            BeanUtils.copyProperties(dictionary, excelVo);
            excelVoList.add(excelVo);
        }
        // 调用方法写入
        try {
            EasyExcel.write(response.getOutputStream(), DataDictionaryExcelVo.class).sheet("数据字典").doWrite(excelVoList);
            return R.ok().message("Excel表导出成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().message("Excel表导出失败！发生IO异常");
        }
    }

    @Override
    public R listDataDictionary(Integer id) {
        QueryWrapper<DataDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<DataDictionary> dataDictionaries = list(wrapper);
        for (DataDictionary dictionary : dataDictionaries) {
            dictionary.setHasChildren(hasChildren(dictionary.getId()));
        }
        return R.ok().data("DataDictionary", dataDictionaries).message("根据表id：" + id + "查询子数据字典成功！");
    }

    private Boolean hasChildren(Integer id) {
        QueryWrapper<DataDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        return count(wrapper) > 0;
    }
}
