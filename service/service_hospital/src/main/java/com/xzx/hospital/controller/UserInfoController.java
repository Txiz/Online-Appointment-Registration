package com.xzx.hospital.controller;


import com.xzx.common.result.R;
import com.xzx.hospital.service.UserInfoService;
import com.xzx.model.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xzx
 * @since 2021-05-08
 */
@RestController
@RequestMapping("/admin/hospital/user-info")
@Api(tags = "用户信息控制器")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation(value = "手机号登录用户")
    @PostMapping("/loginByPhone")
    public R loginByPhone(@RequestBody UserLoginVo userLoginVo) {
        return userInfoService.loginByPhone(userLoginVo);
    }
}

