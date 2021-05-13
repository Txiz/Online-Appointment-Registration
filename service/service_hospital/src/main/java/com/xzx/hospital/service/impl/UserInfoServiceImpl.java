package com.xzx.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzx.common.constant.AuthStatusEnum;
import com.xzx.common.result.R;
import com.xzx.common.util.AuthUtil;
import com.xzx.common.util.JwtUtil;
import com.xzx.hospital.mapper.PatientInfoMapper;
import com.xzx.hospital.mapper.UserInfoMapper;
import com.xzx.hospital.service.UserInfoService;
import com.xzx.model.entity.PatientInfo;
import com.xzx.model.entity.UserInfo;
import com.xzx.model.vo.UserAuthVo;
import com.xzx.model.vo.UserLoginVo;
import com.xzx.model.vo.UserQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
    private PatientInfoMapper patientInfoMapper;

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
        if (userInfo.getIsEnable() != null && !userInfo.getIsEnable().equals(1)) return R.error().message("当前用户未启用！");
        // 封装结果集
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getRealName();
        if (StringUtils.isEmpty(name)) name = userInfo.getNickName();
        if (StringUtils.isEmpty(name)) name = userInfo.getPhone();
        map.put("name", name);
        String token = JwtUtil.generateToken(phone);
        map.put("token", token);
        map.put("head", TOKEN_HEAD);
        return R.ok().data(map).message("登录成功！");
    }

    @Override
    public R authUserInfo(UserAuthVo userAuthVo, HttpServletRequest request) {
        // 获取用户名（手机号）
        String phone = AuthUtil.getUsername(request);
        // 根据用户名查询用户信息
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        UserInfo userInfo = getOne(wrapper);
        // 设置认证信息
        userInfo.setRealName(userAuthVo.getRealName());
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNumber(userAuthVo.getCertificatesNumber());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(AuthStatusEnum.WAIT_AUTH.getStatus());
        // 更新信息，并返回结果
        return updateById(userInfo) ? R.ok().message("认证信息保存成功！") : R.error().message("认证信息保存失败！");
    }

    @Override
    public R getUserInfo(HttpServletRequest request) {
        // 获取用户名（手机号）
        String phone = AuthUtil.getUsername(request);
        // 根据用户名查询用户信息
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        UserInfo userInfo = getOne(wrapper);
        return R.ok().data("userInfo", userInfo).message("获取用户信息成功！");
    }

    @Override
    public R getUserInfoById(Integer id) {
        UserInfo userInfo = setOtherParam(getById(id));
        QueryWrapper<PatientInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<PatientInfo> patientInfoList = patientInfoMapper.selectList(wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        map.put("patientInfoList", patientInfoList);
        return R.ok().data(map).message("获取用户信息成功！");
    }

    @Override
    public R lockUserInfo(Integer id, Integer isEnable) {
        if (isEnable != null) {
            UserInfo userInfo = getById(id);
            userInfo.setIsEnable(isEnable);
            return updateById(userInfo) ? R.ok().message("用户状态修改成功！") : R.error().message("用户状态修改失败！");
        }
        return R.error().message("参数错误！");
    }

    @Override
    public R approvalUserInfo(Integer id, Integer authStatus) {
        if (authStatus != null) {
            UserInfo userInfo = getById(id);
            userInfo.setAuthStatus(authStatus);
            return updateById(userInfo) ? R.ok().message("用户信息认证成功！") : R.error().message("用户信息认证失败！");
        }
        return R.error().message("参数错误！");
    }

    @Override
    public R pageUserInfo(Integer current, Integer size, UserQueryVo userQueryVo) {
        String name = userQueryVo.getKeyword();
        Integer isEnable = userQueryVo.getIsEnable();
        Integer authStatus = userQueryVo.getAuthStatus();
        String beginTime = userQueryVo.getBeginTime();
        String endTime = userQueryVo.getEndTime();
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) wrapper.like("real_name", name);
        if (isEnable != null) wrapper.eq("is_enable", isEnable);
        if (authStatus != null) wrapper.eq("auth_status", authStatus);
        if (!StringUtils.isEmpty(beginTime)) wrapper.ge("create_time", beginTime);
        if (!StringUtils.isEmpty(endTime)) wrapper.le("create_time", endTime);
        Page<UserInfo> userInfoPage = new Page<>(current, size);
        page(userInfoPage, wrapper);
        userInfoPage.getRecords().forEach(this::setOtherParam);
        return R.ok().data("userInfoList", userInfoPage.getRecords()).message("分页查询所有用户信息列表");
    }

    public UserInfo setOtherParam(UserInfo userInfo) {
        userInfo.getParam().put("authStatusString", AuthStatusEnum.getStatusNameByStatus(userInfo.getAuthStatus()));
        userInfo.getParam().put("isEnableString", userInfo.getIsEnable().equals(0) ? "锁定" : "正常");
        return userInfo;
    }
}
