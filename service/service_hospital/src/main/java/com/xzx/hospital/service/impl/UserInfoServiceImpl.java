package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.result.R;
import com.xzx.common.util.JwtUtil;
import com.xzx.hospital.mapper.UserInfoMapper;
import com.xzx.hospital.service.UserInfoService;
import com.xzx.model.entity.UserInfo;
import com.xzx.model.vo.UserLoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.xzx.common.constant.JwtConstant.TOKEN_HEAD;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xzx
 * @since 2021-05-08
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public R loginByPhone(UserLoginVo userLoginVo) {
        // 从用户登录视图对象中获取手机号和验证码
        String phone = userLoginVo.getPhone();
        String code = userLoginVo.getCode();
        if (StringUtils.isEmpty(phone)) return R.error().message("手机号不能为空！");
        if (StringUtils.isEmpty(code)) return R.error().message("验证码不能为空！");
        // 从redis中获取当前有效的验证码
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (!code.equals(redisCode)) return R.error().message("验证码错误！");
        // TODO 跟支付宝绑定
        // 判断是否是第一次登录
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        UserInfo userInfo = getOne(wrapper);
        if (userInfo == null) {
            userInfo = new UserInfo();
            // TODO 填充数据
            userInfo.setPhone(phone);
            save(userInfo);
        }
        // 判断当前用户是否可用
        if (userInfo.getIsEnable() != null && !userInfo.getIsEnable()) return R.error().message("当前用户未启用！");
        // 封装结果集
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getRealName();
        if (StringUtils.isEmpty(name)) name = userInfo.getNickName();
        if (StringUtils.isEmpty(name)) name = userInfo.getPhone();
        map.put("name", name);
        // TODO 生成token
        String token = JwtUtil.generateToken(phone);
        map.put("token", token);
        map.put("head", TOKEN_HEAD);
        return R.ok().data(map).message("登录成功！");
    }
}
