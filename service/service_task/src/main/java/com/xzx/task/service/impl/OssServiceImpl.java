package com.xzx.task.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xzx.common.result.R;
import com.xzx.task.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.xzx.common.constant.AliyunConstant.*;

/**
 * 对象存储，服务实现
 * 作者: xzx
 * 创建时间: 2021-05-12-13-39
 **/
@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传图片文件
     *
     * @param file 需要上传的图片
     * @return 统一结果封装
     */
    @Override
    public R uploadPicture(MultipartFile file) {
        // 获取上传图片名称
        String originalFilename = file.getOriginalFilename();
        // 使用UUID添加随机值，防止同名文件覆盖
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 把文件按日期分类
        String date = new DateTime().toString("yyyy/MM/dd");
        // 设置上传后新的文件名
        String fileName = date + "/" + uuid + "-" + originalFilename;
        // 获取需要的常量
        String endpoint = END_POINT;
        String bucketName = BUCKET_NAME;
        // 创建Oss实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 构造输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 上传文件至阿里云
        ossClient.putObject(bucketName, fileName, inputStream);
        // 关闭实例
        ossClient.shutdown();
        // 获取真实的图片路径
        String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        return R.ok().data("url", url).message("上传图片成功！");
    }
}
