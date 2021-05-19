package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.DataDictionary;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 组织架构表 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-04-29
 */
public interface DataDictionaryService extends IService<DataDictionary> {

    R importDataDictionary(MultipartFile file);

    R exportDataDictionary(HttpServletResponse response);

    R listDataDictionary(Integer id);

    R listByCode(String code);
}
