package com.xzx.hospital.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzx.common.result.R;
import com.xzx.model.entity.UserInfo;
import com.xzx.model.vo.UserAuthVo;
import com.xzx.model.vo.UserLoginVo;
import com.xzx.model.vo.UserQueryVo;

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

    R authUserInfo(UserAuthVo userAuthVo, HttpServletRequest request);

    R getUserInfo(HttpServletRequest request);

    R getUserInfoById(Integer id);

    R lockUserInfo(Integer id, Integer isEnable);

    R approvalUserInfo(Integer id, Integer authStatus);

    R pageUserInfo(Integer current, Integer size, UserQueryVo userQueryVo);
}
