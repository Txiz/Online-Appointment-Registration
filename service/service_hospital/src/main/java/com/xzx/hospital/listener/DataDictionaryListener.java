package com.xzx.hospital.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xzx.hospital.mapper.DataDictionaryMapper;
import com.xzx.model.entity.DataDictionary;
import com.xzx.model.vo.DataDictionaryExcelVo;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * 数据字典监听器
 * 作者: xzx
 * 创建时间: 2021-04-29-23-26
 **/
public class DataDictionaryListener extends AnalysisEventListener<DataDictionaryExcelVo> {

    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public void invoke(DataDictionaryExcelVo dataDictionaryExcelVo, AnalysisContext analysisContext) {
        // 将读取的每行数据插入数据库中
        DataDictionary dictionary = new DataDictionary();
        BeanUtils.copyProperties(dataDictionaryExcelVo, dictionary);
        dataDictionaryMapper.insert(dictionary);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
