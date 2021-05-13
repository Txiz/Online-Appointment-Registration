package com.xzx.task.service;

import com.xzx.common.result.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * 对象存储，服务接口
 * 作者: xzx
 * 创建时间: 2021-05-12-13-39
 **/
public interface OssService {

    /**
     * 上传图片文件
     *
     * @param file 需要上传的图片
     * @return 统一结果封装
     */
    R uploadPicture(MultipartFile file);
}
