package com.xzx.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 数据字典转Excel表视图
 * 作者: xzx
 * 创建时间: 2021-04-29-23-23
 **/
@Data
public class DataDictionaryExcelVo {

    @ExcelProperty(value = "id", index = 0)
    private Integer id;

    @ExcelProperty(value = "父id", index = 1)
    private Integer parentId;

    @ExcelProperty(value = "名称", index = 2)
    private String name;

    @ExcelProperty(value = "值", index = 3)
    private Integer value;

    @ExcelProperty(value = "编码", index = 4)
    private String code;
}
