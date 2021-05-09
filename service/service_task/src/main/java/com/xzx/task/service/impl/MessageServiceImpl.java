package com.xzx.task.service.impl;

import com.xzx.common.result.R;
import com.xzx.common.util.RandomUtil;
import com.xzx.task.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 短信验证，服务实现
 * 作者: xzx
 * 创建时间: 2021-05-08-15-42
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public R getCode(String phone) {
        // 判断redis中有没有验证码
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(redisCode)) return R.error().message("请勿重复发送验证码！");
        // 使用随机数工具生成6位验证码
        String code = RandomUtil.getSixBitRandom();
        // 发送短信
        if (sendMessage(phone, code)) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok().message("验证码短信发送成功！验证码：" + code);
        } else {
            return R.error().message("验证码短信发送失败");
        }
    }

    private boolean sendMessage(String phone, String code) {
        // 判断手机号和验证码是否为空
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) return false;

        return true;
    }
}
