package com.xzx.hospital.mapper;

import com.xzx.model.entity.DataDictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 组织架构表 Mapper 接口
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
public interface DataDictionaryMapper extends BaseMapper<DataDictionary> {

    String getNameByValue(String value);
}
