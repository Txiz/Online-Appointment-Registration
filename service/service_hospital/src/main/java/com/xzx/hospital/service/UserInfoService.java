package com.xzx.hospital.service;

import com.xzx.common.result.R;
import com.xzx.model.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.model.vo.UserLoginVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xzx
 * @since 2021-05-08
 */
public interface UserInfoService extends IService<UserInfo> {

    R loginByPhone(UserLoginVo userLoginVo);
}
